import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class Build {

  /**
   * Prints words that are reachable from the given vertex and are strictly shorter than k characters.
   * If the vertex is null or no reachable words meet the criteria, prints nothing.
   *
   * @param vertex the starting vertex
   * @param k the maximum word length (exclusive)
   */
  public static void printShortWords(Vertex<String> vertex, int k) {
    Set<Vertex<String>> visit = new HashSet<>();
    printShortWords(vertex, k, visit);
  }
  public static void printShortWords(Vertex<String> vertex, int k, Set<Vertex<String>> visited) {
    if(vertex == null || visited.contains(vertex)) return;
    visited.add(vertex);
    String word = vertex.data;
    if(word.toCharArray().length < k) {
      System.out.println(word);
    }
    for(Vertex<String> newWords : vertex.neighbors) {
      printShortWords(newWords, k, visited);
    }
  }

  /**
   * Returns the longest word reachable from the given vertex, including its own value.
   *
   * @param vertex the starting vertex
   * @return the longest reachable word, or an empty string if the vertex is null
   */
  public static String longestWord(Vertex<String> vertex) {
    Set<Vertex<String>> visit = new HashSet<>();
    return longestWord(vertex, visit, "");
  }
  public static String longestWord(Vertex<String> vertex, Set<Vertex<String>> visited, String max) {
    if(vertex == null || visited.contains(vertex)) return "";
    visited.add(vertex);
    if(max.length() < vertex.data.length()) max = vertex.data;
    for(Vertex<String> word : vertex.neighbors) {
      String newWord = longestWord(word, visited, max);
      if(newWord.length() > max.length()) max = newWord;
    }
    return max;
  }

  /**
   * Prints the values of all vertices that are reachable from the given vertex and 
   * have themself as a neighbor.
   *
   * @param vertex the starting vertex
   * @param <T> the type of values stored in the vertices
   */
  public static <T> void printSelfLoopers(Vertex<T> vertex) {
    Set<Vertex<T>> set = new HashSet<>();
    printSelfLoopers(vertex, set);
  }
  public static <T> void printSelfLoopers(Vertex<T> vertex, Set<Vertex<T>> visit) {
    if(vertex == null || visit.contains(vertex)) return;
    visit.add(vertex);
    for(Vertex<T> neighbor : vertex.neighbors) {
      if(neighbor == vertex) {
        System.out.println(vertex.data);
      }
      printSelfLoopers(neighbor, visit);
    }
  }

  /**
   * Determines whether it is possible to reach the destination airport through a series of flights
   * starting from the given airport. If the start and destination airports are the same, returns true.
   *
   * @param start the starting airport
   * @param destination the destination airport
   * @return true if the destination is reachable from the start, false otherwise
   */
  public static boolean canReach(Airport start, Airport destination) {
    Set<Airport> visit = new HashSet<>();
    return canReach(start, destination, visit);
  }
  public static boolean canReach(Airport start, Airport destination, Set<Airport> visited) {
    if(start == null || destination == null || visited.contains(start)) return false;
    visited.add(start);
    for(Airport next : start.getOutboundFlights()) {
      canReach(next, destination, visited);
    }
    if(visited.contains(destination)) return true;
    return false;
  }

  /**
   * Returns the set of all values in the graph that cannot be reached from the given starting value.
   * The graph is represented as a map where each vertex is associated with a list of its neighboring values.
   *
   * @param graph the graph represented as a map of vertices to neighbors
   * @param starting the starting value
   * @param <T> the type of values stored in the graph
   * @return a set of values that cannot be reached from the starting value
   */
  public static <T> Set<T> unreachable(Map<T, List<T>> graph, T starting) {
    Set<T> seen = new HashSet<>();
    Set<T> unseen = new HashSet<>();
    return unreachable(graph, starting, seen, unseen);
  }
  public static <T> Set<T> unreachable(Map<T, List<T>> graph, T starting, Set<T> visited, Set<T> unseen) {
    if(graph == null || starting == null || visited.contains(starting)) return new HashSet<>();
    if(!graph.containsKey(starting)) {
      return graph.keySet();
    }

    visited.add(starting);
    for(T next : graph.get(starting)) {
      unreachable(graph, next, visited, unseen);
    }

    unseen.clear();
    for(T allPoint : graph.keySet()) {
      if(!visited.contains(allPoint)) {
        unseen.add(allPoint);
      }
    }
    return unseen;
  }
}
