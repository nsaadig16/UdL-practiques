package Examen_22_23.ex1;
import acm.program.CommandLineProgram;
import java.io.*;
import java.util.StringTokenizer;

public class ProcessResults extends CommandLineProgram {
    private static final String TEAMS = "teams.dat";
    private static final String RESULTS = "results.csv";
    private League league;
    private BufferedReader results;
    public void run() {
        try {
            openFiles(RESULTS);
            processResults();
            closeFiles();
        } catch (IOException ex) {
            println("Error");
        }
    }
    private void openFiles(String resultsName) throws IOException {
        league = new League(TEAMS);
        results = new BufferedReader(new FileReader(RESULTS));
    }
    private void closeFiles() throws IOException {
        league.close(); results.close();
    }
    private void processResults() throws IOException {
        String gameResult = results.readLine();
        while (gameResult != null){
            processResult(gameResult);
            gameResult = results.readLine();
        }
    }

    private void processResult(String gameResult) throws IOException {
        StringTokenizer st = new StringTokenizer(gameResult," ");
        while (st.hasMoreTokens()){
            long team1Id = Long.parseLong(st.nextToken());
            long team2Id = Long.parseLong(st.nextToken());
            int goalsTeam1 = Integer.parseInt(st.nextToken());
            int goalsTeam2 = Integer.parseInt(st.nextToken());
            if(league.exists(team1Id) && league.exists(team2Id) && goalsTeam2 > 0 && goalsTeam1 > 0){
                processValidGame(team1Id, team2Id, goalsTeam1, goalsTeam2);
            }
        }
    }

    private void processValidGame(long team1Id, long team2Id, int goalsTeam1, int goalsTeam2) throws IOException {
        Team team1 = league.readTeam(team1Id);
        Team team2 = league.readTeam(team2Id);
        team1.addGameResult(goalsTeam1, goalsTeam2);
        team2.addGameResult(goalsTeam2, goalsTeam1);
        league.writeTeam(team1);
        league.writeTeam(team2);
    }


}
