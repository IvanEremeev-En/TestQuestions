package IvanAndAmit;

import java.io.Serializable;

public abstract class Question implements Serializable {
	public enum eDifficulty {
		Easy, Normal, Hard
	};

	private static int counter;
	private int serialNumber;
	protected String question;
	protected eDifficulty difficulty;

	public boolean setQuestion(String Question) {
		if (Question.isEmpty() || Question.equals(" ")) {
			return false;
		} else {
			if (Question.contains("?")) {
				question = Question;
			} else {
				StringBuffer quest = new StringBuffer(Question);
				quest.append("?");
				question = quest.toString();
			}
			return true;
		}
	}

	public String getQuestion() {
		return question;
	}

	public eDifficulty getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(eDifficulty difficulty) {
		this.difficulty = difficulty;
	}

	public int getSerialNumber() {
		return serialNumber;
	}

	public void setCounter(int newCounter) {
		counter = newCounter;
	}

	public abstract boolean addAnswerToQuestion();

	public Question(String question) {
		setQuestion(question);
		serialNumber = ++counter;
	}

	public Question(Question other) {
		setQuestion(other.question);
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Question other = (Question) obj;
		return other.question.equals(question);
	}

	public String toString() {
		StringBuffer ans = new StringBuffer(question);
		ans.append("\t Difficulty-Level: " + difficulty.name() + "\t (S.N: " + serialNumber + " ) \n");
		return ans.toString();
	}

	public String toStringExam() {
		StringBuffer ans = new StringBuffer(question);
		return ans.toString();
	}

}
