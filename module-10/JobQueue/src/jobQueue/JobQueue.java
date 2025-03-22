package jobQueue;

public class JobQueue {
	PriorityQueue<Job> queue;
	
	public JobQueue() {
		this.queue = new PriorityQueue<Job>();
	}
	
	void insert(Job job) {
		queue.add(job);
	}
	
	void runHighestPriority() {
		Job hpj = queue.remove();
		hpj.execute();
	}

}
