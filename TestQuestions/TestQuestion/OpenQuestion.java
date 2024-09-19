package IvanAndAmit;

import java.io.Serializable;

public class OpenQuestion extends Question implements Serializable {
	private Answer openAnswer;

	public boolean addAnswerToQuestion(String answer) {
		if (answer.isEmpty() || answer.equals(" ")) {
			return false;
		} else {
			openAnswer = new Answer(answer);
			return true;
		}
	}

	public String getOpenAnswer() {
		return openAnswer.getAnswer();
	}

	public void deleteOpenAnswer() {
		openAnswer = null;
	}

	public OpenQuestion(String question, String answer, eDifficulty difficulty) {
		super(question);
		setDifficulty(difficulty);
		addAnswerToQuestion(answer);
	}

	public OpenQuestion(OpenQuestion other) {
		super(other);
		this.difficulty = other.difficulty;
		addAnswerToQuestion(other.getOpenAnswer());
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OpenQuestion other = (OpenQuestion) obj;
		return other.question.equals(question) && other.getOpenAnswer().equals(openAnswer.getAnswer());
	}

	public String toString() {
		return super.toString() + "Answer: " + openAnswer + "\n";
	}

	public String questionOnlyToString() {
		return super.toStringExam() + "\t" + difficulty.name() + "   (" + getClass().getSimpleName() + ")\n";
	}

	public String toStringExam() {
		return super.toStringExam() + "\n";
	}

	@Override
	public boolean addAnswerToQuestion() {
		// TODO Auto-generated method stub
		return false;
	}

}
