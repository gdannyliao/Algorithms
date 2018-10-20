package com.ggdsn.algorithms.alg4work.baseballelimination;

import com.ggdsn.algorithms.alg4work.Alg4;
import edu.princeton.cs.algs4.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class BaseballElimination {
    private final int teamCount;
    private int[] w, loss, left;
    private int[][] g;
    private String[] teamNames;
    private TreeMap<String, Integer> idx = new TreeMap<>();

    public BaseballElimination(String filename) {
        try {
            System.setIn(new FileInputStream(filename));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("file not found");
        }
        teamCount = StdIn.readInt();
        w = new int[teamCount];
        loss = new int[teamCount];
        left = new int[teamCount];
        g = new int[teamCount][teamCount];
        teamNames = new String[teamCount];
        for (int i = 0; i < teamCount; i++) {
            String teamName = StdIn.readString();
            idx.put(teamName, i);
            teamNames[i] = teamName;
            w[i] = StdIn.readInt();
            loss[i] = StdIn.readInt();
            left[i] = StdIn.readInt();
            for (int j = 0; j < teamCount; j++) {
                g[i][j] = StdIn.readInt();
            }
        }
    } // create a baseball division from given filename in format specified below

    public int numberOfTeams() {
        return teamCount;
    }                       // number of teams

    public Iterable<String> teams() {
        return idx.keySet();
    }                                // all teams

    private void checkTeam(String team) {
        if (!idx.containsKey(team)) throw new IllegalArgumentException();
    }

    public int wins(String team) {
        checkTeam(team);
        return w[idx.get(team)];
    }                  // number of wins for given team

    public int losses(String team) {
        checkTeam(team);
        return loss[idx.get(team)];
    }                 // number of losses for given team

    public int remaining(String team) {
        checkTeam(team);
        return left[idx.get(team)];
    }              // number of remaining games for given team

    public int against(String team1, String team2) {
        checkTeam(team1);
        checkTeam(team2);
        return g[idx.get(team1)][idx.get(team2)];
    }    // number of remaining games between team1 and team2

    public boolean isEliminated(String team) {
        caleTeam(team);
        return eliminated;
    }            // is given team eliminated?

    private Set<String> certifi = new HashSet<>();
    private boolean eliminated = false;
    private String lastTeam;

    private void caleTeam(String team) {
        if (teamCount == 0) throw new IllegalArgumentException();
        checkTeam(team);
        if (team.equals(lastTeam)) return;
        lastTeam = team;
        eliminated = false;
        certifi.clear();
        if (teamCount == 1) {
            //仅一支队伍
            eliminated = true;
            return;
        }

        int selectedTeamIdx = idx.get(team);
        if (teamCount == 2) {
            //仅两支队伍的情况
            int other = selectedTeamIdx == 0 ? 1 : 0;
            eliminated = w[selectedTeamIdx] + left[selectedTeamIdx] < w[other];
            String otherTeam = null;
            for (String t : idx.keySet()) {
                if (!t.equals(team)) {
                    otherTeam = t;
                    break;
                }
            }
            certifi.add(otherTeam);
            return;
        }

        int leftTeamCount = teamCount - 1;
        int gameVertices = getGameVertices(leftTeamCount);
        FlowNetwork network = new FlowNetwork(gameVertices + leftTeamCount + 2);
        int startVertex = 0;
        int endVertex = network.V() - 1;
        int vertexIdx = 1;
        for (int gameTeam1Idx = 0; gameTeam1Idx < teamCount; gameTeam1Idx++) {
            if (gameTeam1Idx == selectedTeamIdx) continue;
            int teamVertexCapacity = 0;
            for (int gameTeam2Idx = gameTeam1Idx + 1; gameTeam2Idx < teamCount; gameTeam2Idx++) {
                if (gameTeam2Idx == selectedTeamIdx) continue;
                //创建从头(teamCount^2为ID） 节点到比赛节点的边
                network.addEdge(new FlowEdge(startVertex, vertexIdx, g[gameTeam1Idx][gameTeam2Idx]));
                //创建从比赛节点到队伍节点的边
                network.addEdge(new FlowEdge(vertexIdx, gameTeam1Idx + gameVertices, g[gameTeam1Idx][gameTeam2Idx]));
                //第一条边的指向节点gameTeam1Idx+gameVertices, 第二条边的指向节点gameVertices+gameTeam2Idx
                int otherVertexIdx = gameVertices + gameTeam2Idx;
                network.addEdge(new FlowEdge(vertexIdx, otherVertexIdx, g[gameTeam1Idx][gameTeam2Idx]));
                vertexIdx++;
                teamVertexCapacity += g[gameTeam1Idx][gameTeam2Idx];
            }
            //创建队伍节点到尾节点（teamCount^2+1)的边
            network.addEdge(new FlowEdge(gameTeam1Idx + gameVertices, endVertex, teamVertexCapacity));
        }

        FordFulkerson fordFulkerson = new FordFulkerson(network, startVertex, endVertex);
        int maxFlowTeamIdx = selectedTeamIdx;
        Iterable<FlowEdge> edges = network.adj(endVertex);
        for (FlowEdge e : edges) {
            if (e.flow() == fordFulkerson.value()) {
                maxFlowTeamIdx = e.other(endVertex) - gameVertices;
                break;
            }
        }
        if (fordFulkerson.value() + w[maxFlowTeamIdx] > w[selectedTeamIdx] + left[selectedTeamIdx]) {
            eliminated = true;
            //TODO 看看如何运行其他最大流方案，目前只运行了其中之一。这种方式并不正确，需要探索其他的最大流方案
            certifi.add(teamNames[maxFlowTeamIdx]);
        }
    }

    private int getGameVertices(int teamCount) {
        int res = 1;
        for (int i = 2; i < teamCount; i++) {
            res += i;
        }
        return res;
    }

    public Iterable<String> certificateOfElimination(String team) {
        return certifi;
    }  // subset R of teams that eliminates given team; null if not eliminated

    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination(Alg4.getFile("baseball" + File.separator + args[0]));
//        BaseballElimination division = new BaseballElimination(args[0]);
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            } else {
                StdOut.println(team + " is not eliminated");
            }
        }
    }
}
