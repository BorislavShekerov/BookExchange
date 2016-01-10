package com.bookexchange.graph;

import com.bookexchange.dto.User;

import java.util.*;

/**
 * Created by sheke on 11/17/2015.
 */
public class GraphConstructor {

    private List<User> users;

    public GraphConstructor(List<User> user) {
        this.users = user;
    }

    public Graph<String> constructGraph() {
        Map<String, List<String>> categoryToUserListMap = constructBookCategoryToUsersMap();
        Map<String, Vertex<String>> usernameToVertexMap = new HashMap<>();
        Set<Edge<String>> edgesList = new HashSet<>();

        users.stream().forEach(user -> {
            Vertex userVertex;
            if (!usernameToVertexMap.containsKey(user.getUsername())) {
                userVertex = new Vertex(user.getUsername());
                usernameToVertexMap.put(user.getUsername(), userVertex);
            } else userVertex = usernameToVertexMap.get(user.getUsername());

            user.getBooksPostedOnExchange().stream().map(book -> {
                return book.getCategory().getCategoryName();
            }).forEach(categoryOfBookPosted -> {
                List<String> usersInterestedInCategory = categoryToUserListMap.get(categoryOfBookPosted);

                if(usersInterestedInCategory != null) {
                    usersInterestedInCategory.forEach(userInterestedInCategory -> {
                        Vertex interestedUserVertex;
                        if (!usernameToVertexMap.containsKey(userInterestedInCategory)) {
                            interestedUserVertex = new Vertex(userInterestedInCategory);
                            usernameToVertexMap.put(userInterestedInCategory, interestedUserVertex);
                        } else interestedUserVertex = usernameToVertexMap.get(userInterestedInCategory);

                        edgesList.add(new Edge<String>(userVertex, interestedUserVertex));
                    });
                }
            });
        });

        return buildGraph(usernameToVertexMap, edgesList);
    }

    private Graph<String> buildGraph(Map<String, Vertex<String>> usernameToVertexMap,Set<Edge<String>> edges) {
        List<Vertex<String>> verticiesList = new ArrayList<>(usernameToVertexMap.values());

        Graph<String> graph = new Graph<>(verticiesList, new ArrayList<>());
        edges.stream().forEach( edge -> graph.addEdge(edge.getFrom(),edge.getTo(),0));

        return graph;
    }

    private Map<String, List<String>> constructBookCategoryToUsersMap() {
        Map<String, List<String>> categoryToUserListMap = new HashMap<>();

        users.stream().forEach(user -> {
            user.getCategoriesInterestedIn().stream().forEach(bookCategory -> {
                if (categoryToUserListMap.containsKey(bookCategory.getCategoryName())) {
                    categoryToUserListMap.get(bookCategory.getCategoryName()).add(user.getUsername());
                } else {
                    categoryToUserListMap.put(bookCategory.getCategoryName(), new ArrayList<String>(Arrays.asList(user.getUsername())));
                }
            });
        });

        return categoryToUserListMap;
    }
}
