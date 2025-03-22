package jobQueue;

public class Job implements Comparable<Job> {
	  private String name;
	  private int priority;

	  Job(String name, int priority) {
	    this.name = name;
	    this.priority = priority;
	  }

	  void execute() {
	    System.out.println("Running the job with name " + this.name + " and priority " + this.priority);
	  }

	  String getName() {
	    return this.name;
	  }

	  int getPriority() {
	    return this.priority;
	  }
	  
	  @Override
	  public int compareTo(Job other) {
	        // If we want jobs with higher priority to come out first in a max-heap,
	        // we just compare this.priority to other.priority normally.
	        return Integer.compare(this.priority, other.priority);
	  }
}