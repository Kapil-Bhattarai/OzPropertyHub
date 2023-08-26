package chat;

import customer.CustomerEJB;
import jakarta.ejb.EJB;
import jakarta.faces.bean.ApplicationScoped;
import jakarta.faces.bean.SessionScoped;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Base64;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

@ServerEndpoint("/chat/{roomId}")
@ApplicationScoped
public class ChatWebSocket {

    private static final ConcurrentHashMap<String, CopyOnWriteArrayList<Session>> roomSessionsMap = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, CopyOnWriteArrayList<Chat>> roomMessagesMap = new ConcurrentHashMap<>();

    @EJB
    private ChatEJB chatEJB;

    @OnOpen
    public void onOpen(Session session, @PathParam("roomId") String roomId) {
        System.out.println("hello");
        roomSessionsMap.computeIfAbsent(roomId, key -> new CopyOnWriteArrayList<>()).add(session);
        sendRoomMessages(session, roomId);
    }

    @OnMessage
    public void onMessage(String message, Session session, @PathParam("roomId") String roomId) {
        System.out.println("message");
        JsonObject jsonMessage = Json.createReader(new StringReader(message)).readObject();
        System.out.println("message");
        String type = jsonMessage.getString("type");
        System.out.println("message");
        String messageStr = jsonMessage.getString("message");
        System.out.println("message");
        String attachment = "";
        if ("file".equals(type)) {
            try {
                String content = jsonMessage.getString("content");
                String filename = jsonMessage.getString("filename");

                // Decode content from base64 and save the file or perform actions
                byte[] decodedBytes = Base64.getDecoder().decode(content);
                // Define the path where you want to save the uploaded file
                Map<String, String> envVariables = System.getenv();

                String path = System.getenv("OZPROPERTYHUB_UPLOAD_LOCATION");
                if (path == null) {
                    path = "/Users/louisevanrooyen/codehome/COIT20273/OzPropertyHub/uploads";
                }

                Path filePath = Path.of(path, filename);

                Files.write(filePath, decodedBytes, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
                attachment = filename;
                // Perform additional actions if needed
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            //} else {
            //  messageStr = jsonMessage.getString("content");
            // Handle regular text messages
            //chatEJB.addChat(new Chat(jsonMessage.getString("content")));
//        broadcastMessage(roomId, jsonMessage.getString("content"));
//        saveRoomMessage(roomId, jsonMessage.getString("content"));
        }

        Chat chat = new Chat(messageStr, attachment);
//        chatEJB.addChat(new Chat(message));
        broadcastMessage(roomId, chat);
        saveRoomMessage(roomId, chat);
    }

    @OnClose
    public void onClose(Session session, @PathParam("roomId") String roomId) {
        roomSessionsMap.computeIfPresent(roomId, (key, value) -> {
            value.remove(session);
            return value.isEmpty() ? null : value;
        });
    }

    private static void broadcastMessage(String roomId, Chat message) {
        CopyOnWriteArrayList<Session> roomSessions = roomSessionsMap.get(roomId);
        if (roomSessions != null) {
            for (Session session : roomSessions) {
                try {
                    String jsonMessage = toJson(message); // Convert Chat object to JSON

                    session.getBasicRemote().sendObject(jsonMessage);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (EncodeException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private static void saveRoomMessage(String roomId, Chat message) {
        roomMessagesMap.computeIfAbsent(roomId, key -> new CopyOnWriteArrayList<>()).add(message);
    }

    private static void sendRoomMessages(Session session, String roomId) {
        CopyOnWriteArrayList<Chat> messages = roomMessagesMap.get(roomId);
        if (messages != null) {
            for (Chat message : messages) {
                try {
                    String jsonMessage = toJson(message); // Convert Chat object to JSON
                    session.getBasicRemote().sendObject(jsonMessage);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (EncodeException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private static String toJson(Chat chat) {
        JsonObject jsonObject = Json.createObjectBuilder()
                .add("message", chat.getMessage())
                .add("attachment", chat.getAttachment())
                .build();

        return jsonObject.toString();
    }
}
