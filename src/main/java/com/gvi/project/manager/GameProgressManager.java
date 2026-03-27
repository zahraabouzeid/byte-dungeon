package com.gvi.project.manager;

import com.gvi.project.GamePanel;
import com.gvi.project.helper.SaveData;
import com.gvi.project.models.objects.KeyType;
import com.gvi.project.models.objects.OBJ_Boots;
import com.gvi.project.models.objects.OBJ_Door;
import com.gvi.project.models.objects.OBJ_EventTrigger;
import com.gvi.project.models.objects.OBJ_HealingPotion;
import com.gvi.project.models.objects.OBJ_Key;
import com.gvi.project.models.objects.OBJ_QuizStation;
import com.gvi.project.models.objects.SuperObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GameProgressManager {
	private static final Logger log = LoggerFactory.getLogger(GameProgressManager.class);

    private final Map<String, List<SaveData.SavedObject>> mapSnapshots = new HashMap<>();

    public void snapshotCurrentMap(GamePanel gp) {
        if (gp.currentMap == null) return;
        String key = mapKey(gp.currentMap.name);
        List<SaveData.SavedObject> snapshot = new ArrayList<>();
        for (SuperObject obj : gp.obj) {
            if (obj == null) continue;
            boolean doorOpen  = (obj instanceof OBJ_Door d)          && d.isOpen();
            boolean quizDone  = (obj instanceof OBJ_QuizStation qs)   && qs.completed;
            boolean triggered = (obj instanceof OBJ_EventTrigger et)   && et.isTriggered();
            List<Integer> answered = (obj instanceof OBJ_QuizStation qs)
                    ? new ArrayList<>(qs.getAnsweredQuestionIds())
                    : null;
            snapshot.add(new SaveData.SavedObject(
                    obj.getClass().getSimpleName(),
                    obj.id,
                    obj.worldX, obj.worldY,
                    quizDone, doorOpen, triggered, answered
            ));
        }
        mapSnapshots.put(key, snapshot);
		log.debug("Captured snapshot for map {} with {} objects.", key, snapshot.size());
    }

    public void applySnapshotIfPresent(GamePanel gp) {
        if (gp.currentMap == null) return;
        String key = mapKey(gp.currentMap.name);
        List<SaveData.SavedObject> snapshot = mapSnapshots.get(key);
        if (snapshot == null) {
			log.debug("No snapshot available for map {}.", key);
			return;
		}
		log.debug("Applying snapshot for map {} with {} objects.", key, snapshot.size());
        applySnapshot(gp, snapshot);
    }

    public void restoreFrom(Map<String, List<SaveData.SavedObject>> snapshots) {
        mapSnapshots.clear();
        if (snapshots != null) mapSnapshots.putAll(snapshots);
		log.debug("Restored {} map snapshots from save data.", mapSnapshots.size());
    }

    public Map<String, List<SaveData.SavedObject>> getSnapshots() {
        return Collections.unmodifiableMap(mapSnapshots);
    }

    public void reset() {
        mapSnapshots.clear();
    }

    private void applySnapshot(GamePanel gp, List<SaveData.SavedObject> snapshot) {
        Map<String, SaveData.SavedObject> lookup = new HashMap<>();
        for (SaveData.SavedObject so : snapshot) {
            lookup.put(objectKey(so.className, so.worldX, so.worldY), so);
        }

		// Synchronize the live object list with the saved snapshot in three passes:
		// remove missing objects, restore matching ones, then respawn items that only
		// exist in the snapshot.
        Set<String> matched = new HashSet<>();
        for (int i = gp.obj.size() - 1; i >= 0; i--) {
            SuperObject obj = gp.obj.get(i);
            if (obj == null) continue;
            String k = objectKey(obj.getClass().getSimpleName(), obj.worldX, obj.worldY);
            SaveData.SavedObject saved = lookup.get(k);
            if (saved == null) {
				log.debug("Removing object {} because it is missing from the snapshot.", k);
                gp.obj.remove(i);
            } else {
                matched.add(k);
                restoreObjectState(obj, saved, gp);
            }
        }

        for (SaveData.SavedObject so : snapshot) {
            String k = objectKey(so.className, so.worldX, so.worldY);
            if (matched.contains(k)) continue;
            SuperObject spawned = recreateObject(so);
            if (spawned != null) {
                spawned.worldX = so.worldX;
                spawned.worldY = so.worldY;
                gp.obj.add(spawned);
				log.debug("Respawned object {} from snapshot.", k);
            }
        }
    }

    private SuperObject recreateObject(SaveData.SavedObject so) {
        return switch (so.className) {
            case "OBJ_Key" -> {
                for (KeyType kt : KeyType.values()) {
                    if (kt.getSpriteGroupID().equals(so.objectId)) {
                        yield new OBJ_Key(kt);
                    }
                }
                yield null;
            }
            case "OBJ_Boots"         -> new OBJ_Boots();
            case "OBJ_HealingPotion" -> new OBJ_HealingPotion();
			default -> {
				log.warn("Cannot recreate unsupported saved object type {}.", so.className);
				yield null;
			}
        };
    }

    private void restoreObjectState(SuperObject obj, SaveData.SavedObject saved, GamePanel gp) {
        if (obj instanceof OBJ_Door door && saved.doorOpen) {
            door.setOpen();
        }
        if (obj instanceof OBJ_QuizStation qs) {
            if (saved.quizCompleted) {
                qs.completed = true;
            } else if (saved.answeredQuestionIds != null && !saved.answeredQuestionIds.isEmpty()) {
                qs.restoreProgress(saved.answeredQuestionIds, gp.questionProvider);
            }
        }
        if (obj instanceof OBJ_EventTrigger et && saved.triggered) {
            et.setTriggered();
        }
    }

    private static String mapKey(String mapName) {
        return mapName.toUpperCase();
    }

    private static String objectKey(String className, int x, int y) {
        return className + "@" + x + "," + y;
    }
}
