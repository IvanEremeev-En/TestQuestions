package IvanAndAmit;

import java.io.Serializable;
import java.util.Arrays;

public class Subjects implements Serializable {
	private Stock[] allSubjects;
	private int numOfSubjects;

	public Subjects(int numOfSubjects) {
		allSubjects = new Stock[numOfSubjects];
	}

	public boolean addSubject(Stock StockToAdd) throws Exception {
		if (StockToAdd == null) {
			return false;
		}
		for (int i = 0; i < numOfSubjects; i++) {
			if (StockToAdd.getStockName().equals(allSubjects[i].getStockName())) {
				throw new Exception("Exception: This subject already exist!");
			}
		}
		if (allSubjects.length == numOfSubjects) {
			allSubjects = Arrays.copyOf(allSubjects, allSubjects.length + 1);
		}

		allSubjects[numOfSubjects++] = new Stock(StockToAdd.getNumOfAllQuestionsInTheStock(),
				StockToAdd.getStockName());

		for (int q = 0; q < StockToAdd.getNumOfAllQuestionsInTheStock(); q++) {
			if (StockToAdd.getAllquestionsFromtheStock()[q] instanceof AmericanQuestions) {
				AmericanQuestions tempQuestion = (AmericanQuestions) StockToAdd.getAllquestionsFromtheStock()[q];
				if (q == 0) {
					tempQuestion.setCounter(0);
				}
				allSubjects[numOfSubjects - 1].addAmericanQuestionToTheStock(tempQuestion.getQuestion(),
						tempQuestion.getNumberOfAnswers(), tempQuestion.getDifficulty());
				for (int a = 0; a < tempQuestion.getNumberOfAnswers(); a++) {
					allSubjects[numOfSubjects - 1].addAnswerToQuestionFromStock(
							tempQuestion.getAnswersToQuestion()[a].getAnswer(),
							tempQuestion.getAnswersToQuestion()[a].isItTrueOrFalse(), tempQuestion.getQuestion());
				}

			} else {
				OpenQuestion tempOpenQuestion = (OpenQuestion) StockToAdd.getAllquestionsFromtheStock()[q];
				if (q == 0) {
					tempOpenQuestion.setCounter(0);
				}
				allSubjects[numOfSubjects - 1].addOpenQuestionToTheStock(tempOpenQuestion.getQuestion(),
						tempOpenQuestion.getOpenAnswer(), tempOpenQuestion.getDifficulty());
			}

		}
		return true;
	}

	public boolean deleteSubject(String subjectName) {
		for (int i = 0; i < numOfSubjects; i++) {
			if (allSubjects[i].getStockName().equals(subjectName)) {
				allSubjects[i] = allSubjects[numOfSubjects - 1];
				allSubjects[numOfSubjects - 1] = null;
				numOfSubjects--;
				return true;
			}
		}
		return false;
	}

	public Stock[] getAllSubjects() {
		return allSubjects;
	}

	public int getNumOfSubjects() {
		return numOfSubjects;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Subjects other = (Subjects) obj;
		if (numOfSubjects != other.numOfSubjects)
			return false;
		for (int i = 0; i < numOfSubjects; i++) {
			if (!(allSubjects[i].equals(other.allSubjects[i])))
				return false;
		}
		return true;

	}

	public String toString() {
		StringBuffer str = new StringBuffer("Number of subject in DataBase is " + numOfSubjects + " :\n");
		for (int i = 0; i < numOfSubjects; i++) {
			str.append("Subject number " + (i + 1) + " is " + allSubjects[i].getStockName() + " :\n"
					+ allSubjects[i].toString() + "\n");
		}
		return str.toString();
	}

}
