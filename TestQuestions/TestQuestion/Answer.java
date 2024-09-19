package IvanAndAmit;

import java.io.Serializable;

public class Answer implements Serializable {
	protected String answer;

	public boolean setAnswer(String answer) {
		if (answer.isEmpty() || answer.equals(" ")) {
			return false;
		} else {
			this.answer = answer;
			return true;
		}
	}

	public String getAnswer() {
		return answer;
	}

	public void deleteAnswer() {
		answer = null;
	}

	public Answer(String answer) {
		setAnswer(answer);
	}

	public Answer(Answer other) {
		setAnswer(other.answer);

	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Answer other = (Answer) obj;
		return other.answer.equals(answer);
	}

	public String toString() {
		StringBuffer ans = new StringBuffer(answer);
		return ans.toString();
	}

}
