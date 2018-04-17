package Stolen;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
// w w  w  .  j  a v a  2s .  c o  m
class Philosopher implements Runnable {
	State hlp;
	Chopstick l, r;
	int id;
	public Philosopher(int id, Chopstick l, Chopstick r, State i) {
		this.hlp = i;
		this.l = l;
		this.r = r;
		this.id = id;
	}

	private void eat() {
		try {
			Thread.sleep(2000);
			System.out.println(id+" Eat");
		} catch (Exception e) {
		}
	}

	private void think() {
		try {
			Thread.sleep(2000);
			System.out.println(id + " think");
		} catch (Exception e) {
		}
	}

	public void run() {
		while (true) {
			hlp.grabChopsticks(id, l, r);
			eat();
			hlp.releaseChopsticks(id, l, r);
			think();
		}
	}


	public static void main(String[] args) {
		Chopstick[] s = new Chopstick[5];
		Philosopher[] f = new Philosopher[5];
		State hlp = new State();
		for (int i = 0; i < 5; i++) {
			s[i] = new Chopstick();
		}
		for (int i = 0; i < 5; i++) {
			f[i] = new Philosopher(i, s[i], s[(i + 4) % 5], hlp);
			new Thread(f[i]).start();
		}
	}
}




class Chopstick {
	private boolean availability;

	public Chopstick() {
		availability = true;
	}

	public boolean getAvailability() {
		return availability;
	}

	public void setAvailability(boolean flag) {
		availability = flag;
	}
}

class State {
	Lock mutex = new ReentrantLock();
	Condition[] cond = new Condition[5];
	String[] state = new String[5];
	int[] id = new int[5];

	void outputState(int id) {
		StringBuffer line = new StringBuffer();
		for (int i = 0; i < 5; i++){
			line.append(state[i] + " ");
		}
		System.out.println(line + "(" + (id + 1) + ")");
	}

	public State() {
		for (int i = 0; i < 5; i++) {
			id[i] = i;
			state[i] = "O";
			cond[i] = mutex.newCondition();
		}
	}

	public void setState(int id, String s) {
		state[id] = s;
	}

	public void grabChopsticks(int id, Chopstick l, Chopstick r) {
		mutex.lock();
		try {
			setState(id, "o");
			while (!l.getAvailability() || !r.getAvailability()) {
				cond[id].await();
			}
			l.setAvailability(false);
			r.setAvailability(false);
			setState(id, "X");
			outputState(id);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mutex.unlock();
		}
	}

	public void releaseChopsticks(int id, Chopstick l, Chopstick r) {
		mutex.lock();
		setState(id, "O");
		l.setAvailability(true);
		r.setAvailability(true);
		cond[(id + 1) % 5].signalAll();
		cond[(id + 4) % 5].signalAll();
		outputState(id);
		mutex.unlock();
	}
}