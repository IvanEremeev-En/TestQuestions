package IvanAndAmit;

import java.io.Serializable;
import java.util.Arrays;

public class AmericanQuestions extends Question implements Serializable {
	private AmericanAnswer[] questionAnswers;
	private int numOfAnswers;

	public int getNumberOfAnswers() {
		return numOfAnswers;
	}

	public boolean ChangeArrayOfAnswersToQuestion(AmericanAnswer[] newAnswers) { // not used in this version of the code
		if (newAnswers.length <= 8) {
			questionAnswers = newAnswers;
			return true;
		} else {
			return false;
		}
	}

	public boolean addAnswerToQuestion(String answer, boolean trueOrFalse) {
		if ((numOfAnswers >= 8) || answer.equals(null) || (answer.equals(" "))) {
			return false;
		}
		for (int i = 0; i < numOfAnswers; i++) {
			if (answer.equals(questionAnswers[i].getAnswer())) {
				return false;
			}
		}
		if (numOfAnswers == questionAnswers.length) {
			questionAnswers = Arrays.copyOf(questionAnswers, questionAnswers.length + 1);
			questionAnswers[numOfAnswers++] = new AmericanAnswer(answer, trueOrFalse);
			return true;
		} else {
			questionAnswers[numOfAnswers++] = new AmericanAnswer(answer, trueOrFalse);
			return true;
		}
	}

	public void AddDefaultAnswersAutoExam() throws Exception {
		int trueAnswers = 0;
		AmericanAnswer defualt1 = new AmericanAnswer("There is not true answer", false);
		for (int i = 0; i < numOfAnswers; i++) {
			if (questionAnswers[i].isItTrueOrFalse() == true) {
				trueAnswers++;
				if (trueAnswers > 1) {
					questionAnswers[i].deleteAnswer();
				}
			}
		}
		if (trueAnswers == 0) {
			defualt1.setTrueOrFalse(true);
			questionAnswers = Arrays.copyOf(questionAnswers, questionAnswers.length + 1);
			questionAnswers[numOfAnswers++] = new AmericanAnswer(defualt1);
		}
		if (trueAnswers == 1) {
			questionAnswers = Arrays.copyOf(questionAnswers, questionAnswers.length + 2);
			questionAnswers[numOfAnswers++] = new AmericanAnswer(defualt1);
		}
	}

	public void addDefaultAnswers() {
		int trueAnswers = 0;
		AmericanAnswer defualt1 = new AmericanAnswer("There is not true answer", false);
		AmericanAnswer defualt2 = new AmericanAnswer("More than one answer is true", false);
		for (int i = 0; i < numOfAnswers; i++) {
			if (questionAnswers[i].isItTrueOrFalse() == true) {
				trueAnswers++;
			}
		}
		if (trueAnswers == 0) {
			defualt1.setTrueOrFalse(true);
			questionAnswers = Arrays.copyOf(questionAnswers, questionAnswers.length + 1);
			questionAnswers[numOfAnswers++] = new AmericanAnswer(defualt1);
			questionAnswers[numOfAnswers++] = new AmericanAnswer(defualt2);
		}
		if (trueAnswers > 1) {
			defualt2.setTrueOrFalse(true);
			for (int i = 0; i < numOfAnswers; i++) {
				if (questionAnswers[i].isItTrueOrFalse() == true) {
					questionAnswers[i].setTrueOrFalse(false);
				}
			}
			questionAnswers = Arrays.copyOf(questionAnswers, questionAnswers.length + 2);
			questionAnswers[numOfAnswers++] = new AmericanAnswer(defualt1);
			questionAnswers[numOfAnswers++] = new AmericanAnswer(defualt2);
		}
		if (trueAnswers == 1) {
			questionAnswers = Arrays.copyOf(questionAnswers, questionAnswers.length + 2);
			questionAnswers[numOfAnswers++] = new AmericanAnswer(defualt1);
			questionAnswers[numOfAnswers++] = new AmericanAnswer(defualt2);
		}

	}

	public AmericanAnswer[] getAnswersToQuestion() {
		return questionAnswers;
	}

	public boolean deleteAnswer(String answerToDelet) throws Exception {
		if (numOfAnswers <= 4) {
			throw new Exception("Each American-question must have at least 3 answers");
		}
		for (int i = 0; i < numOfAnswers; i++) {
			if (answerToDelet.equals(questionAnswers[i].getAnswer())) {
				questionAnswers[i] = questionAnswers[numOfAnswers - 1];
				questionAnswers[numOfAnswers - 1] = null;
				numOfAnswers--;
				return true;
			}
		}
		return false;
	}

	public AmericanQuestions(String question, int numOfAnswers, eDifficulty difficulty) {
		super(question);
		setDifficulty(difficulty);
		if (numOfAnswers <= 8) {
			questionAnswers = new AmericanAnswer[numOfAnswers];
		} else {
			questionAnswers = new AmericanAnswer[8];
		}
		this.numOfAnswers = 0;

	}

	public AmericanQuestions(AmericanQuestions other) {
		super(other);
		this.difficulty = other.difficulty;
		questionAnswers = new AmericanAnswer[other.questionAnswers.length];
		numOfAnswers = 0;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AmericanQuestions other = (AmericanQuestions) obj;
		if (numOfAnswers != other.numOfAnswers)
			return false;
		for (int i = 0; i < numOfAnswers; i++) {
			if (!(other.getAnswersToQuestion()[i].equals(questionAnswers[i]))) {
				return false;
			}
		}
		return other.question.equals(question);
	}

	public String toString() {
		StringBuffer ansAm = new StringBuffer();
		for (int i = 0; i < numOfAnswers; i++) {
			ansAm.append("\t" + (i + 1) + ") " + questionAnswers[i].toString() + "\n");
		}
		ansAm.append("\n");
		return super.toString() + ansAm.toString();
	}

	public String questionsOnlyToString() {
		return super.toStringExam() + "\t" + difficulty.name() + "   (" + getClass().getSimpleName() + ")\n";
	}

	public String toStringExam() {
		StringBuffer ansAm = new StringBuffer();
		for (int i = 0; i < numOfAnswers; i++) {
			ansAm.append("\t" + (i + 1) + ") " + questionAnswers[i].toStringExam() + "\n");
		}
		ansAm.append("\n");
		return super.toStringExam() + "\n" + ansAm.toString();
	}

	@Override
	public boolean addAnswerToQuestion() {
		// TODO Auto-generated method stub
		return false;
	}

}
