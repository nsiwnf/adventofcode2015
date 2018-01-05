

import java.util.List;

public class Day12 {

  static int part2(List<String> input) {
    int level = 0;

    int[] potential = new int[20];
    int redLevel = -1;
    for(String line : input) {
      line = line.trim();
      if(line.contains("{")) {
        level++;
      }
      if(line.contains("}")) {
        potential[level-1] += potential[level];
        potential[level] = 0;
        if(redLevel == level) {
          redLevel = -1;
        }
        level--;
      }
      if(redLevel == -1) {
        if(line.contains(": \"red\"")) {
          redLevel = level;
          potential[level] = 0;
        } else {
          int p = 0;
          int sign = 1;
          for(int i =0; i<line.length(); i++) {
            if(line.charAt(i) <= '9' && line.charAt(i) >= '0') {
              p = p*10 + (line.charAt(i) - '0');
            } else if (line.charAt(i) == '-') {
              sign = -1;
            }
          }

          potential[level]+= p*sign;
        }
      }

    }

    return potential[0];
  }


  public static void main(String[] args) {
    List<String> input = Util.readInput("day12.input");
    System.out.println(part2(input));

  }
}
