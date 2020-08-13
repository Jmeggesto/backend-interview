package ai.brace;

public class Main {
    public static void main(String[] args) throws Exception {

        FileDataProcessor a1Processor = new FileDataProcessor("a1.json");
        FileDataProcessor a2Processor = new FileDataProcessor("a2.json");

        TaskSolver taskSolver = new TaskSolver(a1Processor.processFileData(), a2Processor.processFileData());
        taskSolver.solveTaskOne();
        taskSolver.solveTaskTwo();
        taskSolver.solveTaskThree();
        taskSolver.solveTaskFour("output.json");
    }
}
