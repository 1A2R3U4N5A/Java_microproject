import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
abstract class Person {
    protected String name;
    protected int age;
    protected String voterId;
    public Person(String name, int age, String voterId) {
        this.name = name;
        this.age = age;
        this.voterId = voterId;
    }
    public String getName() {
        return name;
    }
    public int getAge() {
        return age;
    }
    public String getVoterId() {
        return voterId;
    }
}
class Voter extends Person {
    private boolean hasVoted;
    public Voter(String name, int age, String voterId) {
        super(name, age, voterId);
        this.hasVoted = false;
    }
    public boolean hasVoted() {
        return hasVoted;
    }
    public void setVoted(boolean hasVoted) {
        this.hasVoted = hasVoted;
    }
}
class VotingSystem {
    private ArrayList<Voter> voters;
    private HashMap<String, Integer> candidates;

    public VotingSystem() {
        voters = new ArrayList<>();
        candidates = new HashMap<>();
        candidates.put("Candidate1", 0);
        candidates.put("Candidate2", 0);
        candidates.put("Candidate3", 0);
    }
    public void registerVoter(Voter voter) {
        voters.add(voter);
    }
    public Voter findVoterById(String voterId) {
        for (Voter voter : voters) {
            if (voter.getVoterId().equals(voterId)) {
                return voter;
            }
        }
        return null;
    }
    public void castVote(String voterId, String candidate) {
        Voter voter = findVoterById(voterId);
        if (voter != null && voter.getAge() >= 18 && !voter.hasVoted()) {
            if (candidates.containsKey(candidate)) {
                candidates.put(candidate, candidates.get(candidate) + 1);
                voter.setVoted(true);
                System.out.println("Thank you, " + voter.getName() + "! Your vote for " + candidate + " has been cast.");
            } else {
                System.out.println("Invalid candidate.");
            }
        } else if (voter == null) {
            System.out.println("Invalid Voter ID.");
        } else if (voter.hasVoted()) {
            System.out.println("You have already voted.");
        } else {
            System.out.println("You must be at least 18 years old to vote.");
        }
    }
    public void displayResults() {
        System.out.println("\nVoting Results:");
        for (Map.Entry<String, Integer> entry : candidates.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " votes");
        }
    }
    public void declareWinner() {
        String winner = null;
        int maxVotes = -1;

        for (Map.Entry<String, Integer> entry : candidates.entrySet()) {
            if (entry.getValue() > maxVotes) {
                maxVotes = entry.getValue();
                winner = entry.getKey();
            }
        }
        if (winner != null) {
            System.out.println("The winner is " + winner + " with " + maxVotes + " votes!");
        } else {
            System.out.println("No votes were cast.");
        }
    }
}
 class OnlineVotingSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        VotingSystem votingSystem = new VotingSystem();
        System.out.print("Enter number of voters to register: ");
        int numberOfVoters = scanner.nextInt();
        scanner.nextLine(); 
        for (int i = 0; i < numberOfVoters; i++) {
            System.out.print("Enter name: ");
            String name = scanner.nextLine();
            System.out.print("Enter age: ");
            int age = scanner.nextInt();
            scanner.nextLine(); 
            System.out.print("Enter Voter ID: ");
            String voterId = scanner.nextLine();
            Voter voter = new Voter(name, age, voterId);
            votingSystem.registerVoter(voter);
            System.out.println("Voter registered successfully.\n");
        }
        char continueVoting;
        do {
            System.out.print("Enter Voter ID to vote: ");
            String voterId = scanner.nextLine();
            System.out.print("Enter candidate name to vote (Candidate1, Candidate2, Candidate3): ");
            String candidate = scanner.nextLine();
            votingSystem.castVote(voterId, candidate);
            System.out.print("Do you want another voter to vote? (y/n): ");
            continueVoting = scanner.nextLine().charAt(0);
        } while (continueVoting == 'y' || continueVoting == 'Y');
        votingSystem.displayResults();
        votingSystem.declareWinner();
        scanner.close();
    }
}
