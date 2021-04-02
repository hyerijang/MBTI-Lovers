package kr.hogink.mbti.MBTILovers.web.chat;


import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class RoomRepository {

    private static Map<String, Room> chatRoomMap; //메모리에 저장

    RoomRepository() {
        chatRoomMap = new LinkedHashMap<>();
    }


    public List<Room> findAllRoom() {
        // 채팅방 생성순서 최근 순으로 반환
        List rooms = new ArrayList(chatRoomMap.values());
        Collections.reverse(rooms);

//        for (String key : chatRoomMap.keySet()) {
//            String value = chatRoomMap.get(key).getName();
//            System.out.println("[key]:" + key + ", [value]:" + value);
//        }
        return rooms;
    }

    public Room findRoomByRid(String rid) {
        return chatRoomMap.get(rid);
    }

    public Room createChatRoom(Room room) {
        chatRoomMap.put(room.getRid(), room);
        return room;
    }
}