package IvanAndAmit;

import java.io.Serializable;

public class AmericanAnswer extends Answer implements Serializable {
	private boolean isTheAnswerRight;

	public void setTrueOrFalse(boolean TrueOrFalse) {
		isTheAnswerRight = TrueOrFalse;
	}

	public boolean isItTrueOrFalse() {
		return isTheAnswerRight;
	}

	public void deleteAmericanAnswer() {
		answer = null;
	}

	public AmericanAnswer(String answer, boolean TrueOrFalse) {
		super(answer);
		setTrueOrFalse(TrueOrFalse);
	}

	public AmericanAnswer(AmericanAnswer other) {
		super(other);
		setTrueOrFalse(other.isTheAnswerRight);
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AmericanAnswer other = (AmericanAnswer) obj;
		return other.answer.equals(answer) && other.isTheAnswerRight == isTheAnswerRight;
	}

	public String toString() {
		return super.toString() + " ---> " + isTheAnswerRight;
	}

	public String toStringExam() {
		return super.toString();
	}

}
