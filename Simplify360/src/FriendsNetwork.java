import java.util.*;

public class FriendsNetwork {
    private Map<String, Set<String>> friendsMap;

    public FriendsNetwork() {
        friendsMap = new HashMap<>();
    }

    // Add a friend to the network
    public void addFriend(String person, String friend) {
        friendsMap.putIfAbsent(person, new HashSet<>());
        friendsMap.putIfAbsent(friend, new HashSet<>());
        friendsMap.get(person).add(friend);
        friendsMap.get(friend).add(person); // Assuming friendship is mutual
    }

    // Get all friends of a person
    public Set<String> getFriends(String person) {
        return friendsMap.getOrDefault(person, new HashSet<>());
    }

    // Get common friends of two people
    public Set<String> getCommonFriends(String person1, String person2) {
        Set<String> friends1 = getFriends(person1);
        Set<String> friends2 = getFriends(person2);
        friends1.retainAll(friends2); // Retain only common friends
        return friends1;
    }

    // Find the nth connection between two friends using BFS
    public int findNthConnection(String start, String end) {
        if (!friendsMap.containsKey(start) || !friendsMap.containsKey(end)) {
            return -1; // If either person does not exist
        }

        Queue<String> queue = new LinkedList<>();
        Map<String, Integer> distances = new HashMap<>();
        queue.add(start);
        distances.put(start, 0);

        while (!queue.isEmpty()) {
            String current = queue.poll();
            int currentDistance = distances.get(current);

            for (String friend : getFriends(current)) {
                if (!distances.containsKey(friend)) {
                    distances.put(friend, currentDistance + 1);
                    queue.add(friend);
                    if (friend.equals(end)) {
                        return currentDistance + 1; // Return the distance if we reach the end
                    }
                }
            }
        }
        return -1; // If no connection found
    }

    public static void main(String[] args) {
        FriendsNetwork network = new FriendsNetwork();

        // Adding friends
        network.addFriend("Alice", "Bob");
        network.addFriend("Alice", "Charlie");
        network.addFriend("Bob", "David");
        network.addFriend("Bob", "Eve");
        network.addFriend("Charlie", "Frank");
        network.addFriend("David", "Janice");
        network.addFriend("Eve", "Janice");

        // Get friends of Alice and Bob
        System.out.println("Alice's friends: " + network.getFriends("Alice"));
        System.out.println("Bob's friends: " + network.getFriends("Bob"));

        // Get common friends
        System.out.println("Common friends of Alice and Bob: " + network.getCommonFriends("Alice", "Bob"));

        // Find nth connection
        System.out.println("Connection between Alice and Janice: " + network.findNthConnection("Alice", "Janice"));
        System.out.println("Connection between Alice and Bob: " + network.findNthConnection("Alice", "Bob"));
        System.out.println("Connection between Bob and Janice: " + network.findNthConnection("Bob", "Janice"));
    }
}