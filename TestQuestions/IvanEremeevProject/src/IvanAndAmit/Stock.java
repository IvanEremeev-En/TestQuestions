package IvanAndAmit;

import java.io.IOException;
import java.io.Serializable;
import java.util.Random;
import java.util.Scanner;

import IvanAndAmit.Question.eDifficulty;

public class Stock implements Serializable, Examable {
	public static Scanner scn = new Scanner(System.in);
	public static Random ran = new Random();
	private String name;
	private AmericanAnswer[] allAmericanAnswers;
	private Answer[] allOpenAnswers;
	private int numOfAmericanAnswers;
	private int numOfOpenAnswers;
	private Question[] allQuestions;
	private int numOfQuestions;
	private static int numOfAllAnswers;

	public Stock(int maxQuestion, String name) throws Exception {
		if (maxQuestion > 10) {
			throw new Exception("Max question for exam is 10!");
		} else {
			this.name = name;
			allQuestions = new Question[maxQuestion];
			numOfQuestions = 0;
			allOpenAnswers = new Answer[maxQuestion];
			allAmericanAnswers = new AmericanAnswer[maxQuestion * 8];
			numOfAmericanAnswers = 0;
		}
	}

	public Stock(Stock other) {
		this.name = other.name;
		this.allQuestions = new Question[other.allQuestions.length];
		numOfQuestions = 0;
		this.allOpenAnswers = new Answer[other.allOpenAnswers.length];
		this.allAmericanAnswers = new AmericanAnswer[allAmericanAnswers.length];
		numOfAmericanAnswers = 0;
	}

	public Stock createManualExam(Stock stockOfAllQuestions, String name) {
		int maxQuestion;
		boolean isValidAnswer = false;
		Stock exam = null;
		while (!isValidAnswer) {
			System.out.println("Please enter the number of Questions for exam (our stock has only "
					+ stockOfAllQuestions.getNumOfAllQuestionsInTheStock() + ") :");
			System.out.println("Enter--> ");
			maxQuestion = scn.nextInt();
			scn.nextLine();
			try {
				exam = makeExam(stockOfAllQuestions, name, maxQuestion);
				// printToFile(exam);
				isValidAnswer = true;

			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		return exam;

	}

	public static Stock makeExam(Stock stockQuestions, String name, int maxQuestion) throws IOException, Exception {
		final int STOP = 0;
		int operatorChoose = 1;
		Stock exam = new Stock(maxQuestion, name);
		System.out.println(stockQuestions.toString()); // The user can see all of his options and choose which one
														// he/she want to in the exam
		for (int i = 0; i < maxQuestion; i++) {
			System.out.println();
			boolean correctQuestionInput = true;
			String question;
			int userAns;
			do {
				System.out.println("Which question from the stock do you want to add ?");
				System.out.println(stockQuestions.allQuestionsToString()); // Prints only the questions to make it
																			// easier
																			// for the user to chose the question for
																			// the
																			// test
				System.out.println("Select the question (Number)-->");
				userAns = scn.nextInt();
				scn.nextLine();
				if (userAns <= stockQuestions.getNumOfAllQuestionsInTheStock()) {
					question = stockQuestions.getAllquestionsFromtheStock()[userAns - 1].getQuestion();
					Question tempQuestion = stockQuestions.getQuestionFromTheStock(question); // Create a copy of the
																								// question so we can
																								// work on it.
					if (tempQuestion instanceof AmericanQuestions) {
						AmericanQuestions theQuestion = (AmericanQuestions) tempQuestion;
						if (i == 0) {
							theQuestion.setCounter(0);
						}
						if (exam.addAmericanQuestionToTheStock(question, theQuestion.getNumberOfAnswers(),
								tempQuestion.getDifficulty())) {
							addAnswers(theQuestion, exam);
							System.out.println("Do you want change the answers? ");
							System.out.println("yes or no-->");
							String ans = scn.nextLine();
							if (ans.equals("yes")) {
								do {
									System.out.println(exam.getAllquestionsFromtheStock()[i].toString()); // Prints the
																											// selected
																											// question
																											// to make
																											// it
																											// easy
																											// for the
																											// user to
																											// decide
																											// which
																											// change to
																											// make
									System.out.println("Do you want add or delete answer?");
									System.out.println("add or delete-->");
									ans = scn.nextLine();
									boolean rightInput = false;
									if (ans.equals("add")) {
										rightInput = true;
										System.out.println(stockQuestions.allAmericanAnswersToString());// Prints all
																										// the options
																										// of answers
										// from the original stock to make
										// it
										// easy for the user to decide.
										System.out.println(
												"Enter other answer from the stock (Maximum 8 answers for each question) :");
										System.out.println("Which answer you would like to add (number)-->");
										int ansNum = scn.nextInt();
										scn.nextLine();
										ans = stockQuestions.getAllAmericanAnswers()[ansNum - 1].getAnswer();
										System.out.println("Is your new answer true or false? ");
										System.out.println("true or false-->");
										boolean boAns = scn.nextBoolean();
										// AmericanAnswer newAns = new AmericanAnswer(ans, boAns);
										exam.addAnswerToQuestionFromStock(ans, boAns, question);
										System.out.println(
												"If you want to stop making changes to the answers PRESS 0 and to continue PRESS 1 ");
										operatorChoose = scn.nextInt();
										scn.nextLine();
									}
									if (ans.equals("delete")) {
										rightInput = true;

										System.out.println("Enter answer to remove:");
										System.out.println("Select answer to remove (Number)-->");
										int userChoose = scn.nextInt();
										scn.nextLine();
										ans = theQuestion.getAnswersToQuestion()[userChoose - 1].getAnswer();
										try {
											exam.deleteAnswerFromQuestion(ans, question);
											System.out.println(
													"If you want to stop making changes to the answers PRESS 0 and to continue PRESS 1");
											operatorChoose = scn.nextInt();
											scn.nextLine();
										} catch (Exception e) {
											System.out.println(e.getMessage());
										}

									}
									if (rightInput == false) {
										System.out.println("Your input is incorrect please try again ");
										System.out.println();
									}

								} while (operatorChoose != STOP);
							}

							exam.addDefaultAnswersToExam(theQuestion.getQuestion());

						} else {
							System.out.println("This question already exist in the exam");
							i--;
						}
					}

					if (tempQuestion instanceof OpenQuestion) {
						OpenQuestion theQuestion = (OpenQuestion) tempQuestion;
						if (i == 0) {
							theQuestion.setCounter(0);
						}
						if (exam.addOpenQuestionToTheStock(question, theQuestion.getOpenAnswer(),
								tempQuestion.getDifficulty())) {
							System.out.println("Do you want change the answer? ");
							System.out.println("yes or no-->");
							String ans = scn.nextLine();
							if (ans.equals("yes")) {
								System.out.println("Write new answer:");
								System.out.println("--->");
								String newAnswer = scn.nextLine();
								exam.switchOpenAnswerToOpenQuestion(question, newAnswer);
							}
						} else {
							System.out.println("This question already exist in the exam");
							i--;
						}
					}

				}

			} while (correctQuestionInput != true);

		}
		return exam;

	}

	public static void addAnswers(AmericanQuestions question, Stock theStock) { // function that helps us to add answers
		// of the
// question to exam file
		for (int i = 0; i < question.getNumberOfAnswers(); i++) {
			theStock.addAnswerToQuestionFromStock(question.getAnswersToQuestion()[i].getAnswer(),
					question.getAnswersToQuestion()[i].isItTrueOrFalse(), question.getQuestion());
		}
	}

	public Stock createAutoMaticExam(Stock stockOfAllQuestions, String name, int numOfQuestions) throws Exception {
		Stock autoExam = new Stock(numOfQuestions, name);
		Question tempQuestion;
		for (int i = 0; i < numOfQuestions; i++) {
			tempQuestion = getAllquestionsFromtheStock()[ran
					.nextInt(stockOfAllQuestions.getNumOfAllQuestionsInTheStock())];
			if (i == 0) {
				tempQuestion.setCounter(0);
			}
			if (tempQuestion instanceof AmericanQuestions) {
				AmericanQuestions theQuestion = (AmericanQuestions) tempQuestion;
				if (autoExam.addAmericanQuestionToTheStock(theQuestion.getQuestion(), theQuestion.getNumberOfAnswers(),
						tempQuestion.getDifficulty())) {
					addAnswers(theQuestion, autoExam);
					theQuestion = (AmericanQuestions) autoExam.getQuestionFromTheStock(tempQuestion.getQuestion());
					if (theQuestion.getNumberOfAnswers() > 4) {

						while (theQuestion.getNumberOfAnswers() != 4) {
							int randomNum = 1 + (ran.nextInt(theQuestion.getNumberOfAnswers()));
							String ans = theQuestion.getAnswersToQuestion()[randomNum - 1].getAnswer();
							if (ans != null) {
								autoExam.deleteAnswerFromQuestion(ans, theQuestion.getQuestion());
							}
						}

					}
					theQuestion.AddDefaultAnswersAutoExam();
				} else {
					i--;
				}
			} else {
				OpenQuestion theOpenQuestion = (OpenQuestion) tempQuestion;
				if (!(autoExam.addOpenQuestionToTheStock(tempQuestion.getQuestion(), theOpenQuestion.getOpenAnswer(),
						tempQuestion.getDifficulty()))) {
					i--;
				}
			}
		}

		return autoExam;
	}

	public boolean addAmericanQuestionToTheStock(String question, int numOfAnswers, eDifficulty difficulty)
			throws Exception {
		if (numOfAnswers <= 3) {
			throw new Exception("Each American-question must have at least 3 answers");
		}
		if (question.isEmpty() || question.equals(" ") || allQuestions.length == numOfQuestions) {
			return false;
		} else {
			for (int i = 0; i < numOfQuestions; i++) {
				if (question.equals(allQuestions[i].getQuestion())) {
					return false;
				}
			}
			allQuestions[numOfQuestions++] = new AmericanQuestions(question, numOfAnswers, difficulty);
			return true;
		}
	}

	public boolean addOpenQuestionToTheStock(String question, String answer, eDifficulty difficulty) {
		if (question.isEmpty() || question.equals(" ") || allQuestions.length == numOfQuestions) {
			return false;
		} else {
			for (int i = 0; i < numOfQuestions; i++) {
				if (question.equals(allQuestions[i].getQuestion())) {
					return false;
				}
			}
			allQuestions[numOfQuestions++] = new OpenQuestion(question, answer, difficulty);

			allOpenAnswers[numOfOpenAnswers++] = new Answer(answer);

			numOfAllAnswers++;
			return true;
		}
	}

	public String getStockName() {
		return name;
	}

	public Question getQuestionFromTheStock(String question) {
		for (int i = 0; i < numOfQuestions; i++) {
			if (allQuestions[i].getQuestion().equals(question))
				return allQuestions[i];
		}
		return null;
	}

	public int getNumOfAllAnswersInStock() {
		return numOfAllAnswers;

	}

	public int getNumOfAllQuestionsInTheStock() {
		return numOfQuestions;
	}

	public Question[] getAllquestionsFromtheStock() {
		return allQuestions;
	}

	public AmericanAnswer[] getAllAmericanAnswers() {
		return allAmericanAnswers;
	}

	public void setNewArrayOfQuestionsToStock(Question[] newQuestions) { // used only in CopyOf when user want to add
																			// question to the stock.
		allQuestions = newQuestions;
	}

	public boolean addAnswerToQuestionFromStock(String answer, boolean trueOrFalse, String question) { // used only in
																										// American
																										// questions
		if (numOfAmericanAnswers == allAmericanAnswers.length)
			return false;
		Question tempQuestion = getQuestionFromTheStock(question);
		if (tempQuestion == null)
			return false;
		else {
			AmericanQuestions theQuestion = (AmericanQuestions) tempQuestion;
			for (int i = 0; i < numOfAmericanAnswers; i++) {
				if (answer.equals(allAmericanAnswers[i].getAnswer())) {

					theQuestion.addAnswerToQuestion(allAmericanAnswers[i].getAnswer(),
							allAmericanAnswers[i].isItTrueOrFalse());
					return true;
				}
			}
			allAmericanAnswers[numOfAmericanAnswers] = new AmericanAnswer(answer, trueOrFalse);
			theQuestion.addAnswerToQuestion(allAmericanAnswers[numOfAmericanAnswers].getAnswer(),
					allAmericanAnswers[numOfAmericanAnswers].isItTrueOrFalse());
			numOfAmericanAnswers++;
			numOfAllAnswers++;
			return true;
		}

	}

	public boolean addDefaultAnswersToExam(String question) { // The function is only used when Exam is creating (There
																// are no default answers in the stock ) and used only
																// on American Questions
		Question tempQuestion = getQuestionFromTheStock(question);
		if (tempQuestion == null)
			return false;
		else {
			AmericanQuestions theQuestion = (AmericanQuestions) tempQuestion;
			theQuestion.addDefaultAnswers();
			return true;
		}
	}

	public boolean deleteAnswerFromQuestion(String answer, String question) throws Exception {
		Question tempQuestion = getQuestionFromTheStock(question);
		if (tempQuestion == null)
			return false;
		else {
			if (tempQuestion instanceof AmericanQuestions) {
				AmericanQuestions theQuestion = (AmericanQuestions) tempQuestion;
				theQuestion.deleteAnswer(answer);
				return true;
			} else {
				OpenQuestion theQuestion = (OpenQuestion) tempQuestion;
				theQuestion.deleteOpenAnswer();
				return true;
			}
		}
	}

	public boolean switchOpenAnswerToOpenQuestion(String question, String newAnswer) {
		Question tempQuestion = getQuestionFromTheStock(question);
		if (tempQuestion == null || newAnswer == null || newAnswer.equals(" "))
			return false;
		else {
			OpenQuestion theQuestion = (OpenQuestion) tempQuestion;
			for (int i = 0; i < numOfOpenAnswers; i++) {
				if (allOpenAnswers[i].getAnswer().equals(newAnswer)) {
					theQuestion.addAnswerToQuestion(allOpenAnswers[i].getAnswer());
					return true;
				}
			}
			for (int t = 0; t < numOfOpenAnswers; t++) {
				if (allOpenAnswers[t].getAnswer().equals(theQuestion.getOpenAnswer())) {
					allOpenAnswers[t] = new Answer(newAnswer);
					theQuestion.addAnswerToQuestion(allOpenAnswers[t].getAnswer());
					return true;
				}
			}
			return false;
		}
	}

	public boolean deleteAnswerFromTheStock(String answer) {

		for (int i = 0; i < numOfAmericanAnswers; i++) {
			if (allAmericanAnswers[i].getAnswer().equals(answer)) {
				allAmericanAnswers[i] = allAmericanAnswers[numOfAmericanAnswers - 1];
				allAmericanAnswers[numOfAmericanAnswers - 1] = null;
				numOfAmericanAnswers--;
				numOfAllAnswers--;
				return true;
			}
		}
		return false;
	}

	public boolean deleteQuestionFromTheStock(String question) {
		for (int i = 0; i < numOfQuestions; i++) {
			if (allQuestions[i].getQuestion().equals(question)) {
				allQuestions[i] = allQuestions[numOfQuestions - 1];
				allQuestions[numOfQuestions - 1] = null;
				numOfQuestions--;
				return true;
			}
		}
		return false;

	}

	public String allAmericanAnswersToString() {
		StringBuffer ans = new StringBuffer("The Stock has " + numOfAmericanAnswers + " answers in total: \n");
		for (int t = 0; t < numOfAmericanAnswers; t++) {
			ans.append((t + 1) + ") " + allAmericanAnswers[t].toString() + "\n");
		}
		return ans.toString();
	}

	public String allQuestionsToString() {
		StringBuffer ans = new StringBuffer("Options from the stock : \n");
		for (int z = 0; z < numOfQuestions; z++) {
			if (allQuestions[z] instanceof AmericanQuestions) {
				AmericanQuestions theQuestion = (AmericanQuestions) allQuestions[z];
				ans.append((z + 1) + ") " + theQuestion.questionsOnlyToString());
			} else {
				OpenQuestion theQuestion = (OpenQuestion) allQuestions[z];
				ans.append((z + 1) + ") " + theQuestion.questionOnlyToString());
			}
		}
		return ans.toString();
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Stock other = (Stock) obj;
		if (numOfQuestions != other.numOfQuestions)
			return false;
		for (int i = 0; i < numOfQuestions; i++) {
			if (!(allQuestions[i].equals(other.getAllquestionsFromtheStock()[i]))) {
				return false;
			}
		}
		return numOfAllAnswers == other.getNumOfAllAnswersInStock();

	}

	public String toString() {
		StringBuffer sb = new StringBuffer("There are " + numOfQuestions + " questions: \n");
		for (int z = 0; z < numOfQuestions; z++) {
			if (allQuestions[z] instanceof AmericanQuestions) {
				AmericanQuestions theQuestion = (AmericanQuestions) allQuestions[z];
				sb.append((z + 1) + ") " + theQuestion.toString() + "\n");
			} else {
				OpenQuestion theQuestion = (OpenQuestion) allQuestions[z];
				sb.append((z + 1) + ") " + theQuestion.toString() + "\n");
			}
		}
		return sb.toString();
	}

	public String toStringExam() {
		StringBuffer sb = new StringBuffer("The Exam has " + numOfQuestions + " questions in total: \n");
		for (int z = 0; z < numOfQuestions; z++) {
			if (allQuestions[z] instanceof AmericanQuestions) {
				AmericanQuestions theQuestion = (AmericanQuestions) allQuestions[z];
				sb.append((z + 1) + ") " + theQuestion.toStringExam() + "\n");
			} else {
				OpenQuestion theQuestion = (OpenQuestion) allQuestions[z];
				sb.append((z + 1) + ") " + theQuestion.toStringExam() + "Answer: \n \n");
			}
		}
		return sb.toString();
	}

	@Override
	public Stock createManualExam(Stock stockOfQuestions) throws IOException, Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Stock createAutoMaticExam(Stock stockOfQuestions, int numOfQuestions) throws IOException, Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
