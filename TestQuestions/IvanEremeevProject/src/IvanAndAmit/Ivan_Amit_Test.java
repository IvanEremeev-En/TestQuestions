package IvanAndAmit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

import IvanAndAmit.Question.eDifficulty;

public class Ivan_Amit_Test {
	public static Scanner scn = new Scanner(System.in);

	public static void main(String[] args)
			throws IOException, FileNotFoundException, ClassNotFoundException, Exception {
		// TODO Auto-generated method stub

		Subjects subjects;
		int chosenSubject;

		// Part of the code that make BinarFile
		// int numberOfQuestionsInTheStock = 7;
		// subjects = new Subjects(2);
		// Stock basic = new Stock(numberOfQuestionsInTheStock,"BasicExam");
		// Stock math= new Stock(3,"Math");
		// makeStockForBasicExam(basic,"BasicExam");
		// makeStockForMathExam(math,"MathExam");
		// subjects.addSubject(basic);
		// subjects.addSubject(math);

		ObjectInputStream inFile = new ObjectInputStream(new FileInputStream("Allsubjects.dat"));
		subjects = (Subjects) inFile.readObject();
		inFile.close();

		System.out.println("Which subject do you want to chose? ");
		for (int s = 0; s < subjects.getNumOfSubjects(); s++) {
			System.out.println((s + 1) + ") " + subjects.getAllSubjects()[s].getStockName());
		}
		System.out.println("\t 0) Make new Subject");
		System.out.println("Write the number of your option--> ");
		chosenSubject = scn.nextInt();
		scn.nextLine();
		if (chosenSubject != 0) {
			Stock stockQuestions = subjects.getAllSubjects()[chosenSubject - 1];
			int stockOrExam;
			final int EXIT = 0;
			do {
				System.out.println("What do you want to do ? ");
				System.out.println("\t 1) Show all questions in the stock");
				System.out.println("\t 2) Change stock of exam questions");
				System.out.println("\t 3) Make new Manual-exam");
				System.out.println("\t 4) Make Auto-Exam");
				System.out.println("\t 0) Exit");
				System.out.println("Write the number of your option--> ");
				stockOrExam = scn.nextInt();
				switch (stockOrExam) {
				case 1:
					System.out.println(subjects.getAllSubjects()[chosenSubject - 1].toString());
					break;
				case 2:
					final int CHANGESTOP = 0;
					int operatorChose;
					do {
						System.out.println();
						System.out.println("If you want add Question to the stock PRESS 1");
						System.out.println("If you want delete Question from the stock PRESS 2");
						System.out.println("If you want add/switch answer to question from the stock PRESS 3 ");
						System.out.println("If you want delete answer from the stock PRESS 4");
						System.out.println("To EXIT PRESS 0");
						operatorChose = scn.nextInt();
						scn.nextLine();
						switch (operatorChose) {
						case 1:
							addQuestionToDataBase(subjects.getAllSubjects()[chosenSubject - 1]);
							break;
						case 2:
							try {
								deleteQuestionFromDataBase(subjects.getAllSubjects()[chosenSubject - 1]);
							} catch (Exception e) {
								System.out.println(e.getMessage());
							}
							break;
						case 3:
							addAnswerToQuestionOnDataBase(subjects.getAllSubjects()[chosenSubject - 1]);
							break;
						case 4:
							try {
								deleteAnswerFromQuestionOnDataBase(subjects.getAllSubjects()[chosenSubject - 1]);
							} catch (Exception e) {
								System.out.println(e.getMessage());
							}
							break;
						case 0:
							System.out.println("Done");
							break;
						default:
							System.out.println("invalid input");
							break;
						}

					} while (operatorChose != CHANGESTOP);

					ObjectOutputStream outFile = new ObjectOutputStream(new FileOutputStream("Allsubjects.dat"));
					outFile.writeObject(subjects);
					outFile.close();

					// printStingToFile(stockQuestions.toStrong(), "New_Stock"); // see all the
					// changes in fill
					break;
				case 3:
					Stock exam = stockQuestions.createManualExam(stockQuestions, stockQuestions.getStockName());
					printToFile(exam);
					break;

				case 4:
					boolean correctInput = false;
					Stock AutoExam = null;
					while (!correctInput) {
						System.out
								.println("How many questions do you want in your Exam?    (The Question-Stock has only "
										+ +stockQuestions.getNumOfAllQuestionsInTheStock() + " questions)");
						int userAns = scn.nextInt();
						if (userAns <= stockQuestions.getNumOfAllQuestionsInTheStock()) {
							AutoExam = stockQuestions.createAutoMaticExam(stockQuestions, stockQuestions.getStockName(),
									userAns);
							correctInput = true;
						} else {
							System.out.println("Number of Queestions must be max "
									+ stockQuestions.getNumOfAllQuestionsInTheStock());
						}

					}

					printToFile(AutoExam);
					break;

				case 0:
					System.out.println("GoodBye!");
					break;
				default:
					System.out.println("Invalid option, try again");
					break;
				}
			} while (stockOrExam != EXIT);

		} else if (chosenSubject == 0) {
			System.out.println("What is the name of new subject?");
			String newName = scn.nextLine();
			System.out.println("How many questions would you like to save? ");
			int numOfQuestions = scn.nextInt();
			Stock newSubject = new Stock(numOfQuestions, newName);
			final int CHANGESTOP = 0;
			int operatorChose;
			do {
				System.out.println();
				System.out.println("If you want add Question to the stock PRESS 1");
				System.out.println("If you want delete Question from the stock PRESS 2");
				System.out.println("If you want add/switch answer to question from the stock PRESS 3 ");
				System.out.println("If you want delete answer from the stock PRESS 4");
				System.out.println("To EXIT PRESS 0");
				operatorChose = scn.nextInt();
				scn.nextLine();
				switch (operatorChose) {
				case 1:
					addQuestionToDataBase(newSubject);
					break;
				case 2:
					deleteQuestionFromDataBase(newSubject);
					break;
				case 3:
					addAnswerToQuestionOnDataBase(newSubject);
					break;
				case 4:
					try {
						deleteAnswerFromQuestionOnDataBase(newSubject);
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
					break;
				case 0:
					System.out.println("Done");
					break;
				default:
					System.out.println("invalid input");
					break;
				}

			} while (operatorChose != CHANGESTOP);

			subjects.addSubject(newSubject);

			ObjectOutputStream outFile = new ObjectOutputStream(new FileOutputStream("Allsubjects.dat"));
			outFile.writeObject(subjects);
			outFile.close();

		}

	}

	public static void makeStockForMathExam(Stock stockQuestionsForExam, String name)
			throws FileNotFoundException, IOException { // used only to create another subject for example
		stockQuestionsForExam.addOpenQuestionToTheStock("2x+5+3x+15=10 , x=?", "x=2 ", eDifficulty.Easy);
		stockQuestionsForExam.addOpenQuestionToTheStock("2x*8=32 , x=?", "x=2 ", eDifficulty.Normal);
		stockQuestionsForExam.addOpenQuestionToTheStock("2x+5+3x+15+10=10 , x=?", "x=-4 ", eDifficulty.Hard);
		stockQuestionsForExam.addOpenQuestionToTheStock("3x-15=3 , x=?", "x=6 ", eDifficulty.Easy);

		// ObjectOutputStream outFile = new ObjectOutputStream(new
		// FileOutputStream(name+".dat"));
		// outFile.writeObject(stockQuestionsForExam);
		// outFile.close();
	}

	public static void makeStockForBasicExam(Stock stockQuestionsForExam, String name) // used only to create dataBase
																						// at first
			throws FileNotFoundException, IOException, ClassNotFoundException, Exception {
		stockQuestionsForExam.addAmericanQuestionToTheStock("What is the capital city of Israel?", 4,
				eDifficulty.Normal);
		AmericanAnswer[] q1 = new AmericanAnswer[6];
		q1[0] = new AmericanAnswer("Tel-Aviv", false);
		q1[1] = new AmericanAnswer("Haifa", false);
		q1[2] = new AmericanAnswer("Jerusalem", true);
		q1[3] = new AmericanAnswer("Eilat", false);
		q1[4] = new AmericanAnswer("Rosh-Pina", false);
		q1[5] = new AmericanAnswer("Ashdod", false);
		for (int i = 0; i < q1.length; i++) {
			stockQuestionsForExam.addAnswerToQuestionFromStock(q1[i].getAnswer(), q1[i].isItTrueOrFalse(),
					"What is the capital city of Israel?");
		}

		stockQuestionsForExam.addAmericanQuestionToTheStock("What is the official language of Israel?", 4,
				eDifficulty.Easy);
		AmericanAnswer[] q2 = new AmericanAnswer[5];
		q2[0] = new AmericanAnswer("Russian", false);
		q2[1] = new AmericanAnswer("Arabic", false);
		q2[2] = new AmericanAnswer("Hebrew", true);
		q2[3] = new AmericanAnswer("English", false);
		q2[4] = new AmericanAnswer("Franch", false);
		for (int i = 0; i < q2.length; i++) {
			stockQuestionsForExam.addAnswerToQuestionFromStock(q2[i].getAnswer(), q2[i].isItTrueOrFalse(),
					"What is the official language of Israel?");
		}

		stockQuestionsForExam.addAmericanQuestionToTheStock("Which sea is located on the western coast of Israel?", 4,
				eDifficulty.Normal);
		AmericanAnswer[] q3 = new AmericanAnswer[4];
		q3[0] = new AmericanAnswer("Dead Sea", false);
		q3[1] = new AmericanAnswer("Sea of Galilee", false);
		q3[2] = new AmericanAnswer("Red Sea", false);
		q3[3] = new AmericanAnswer("Mediterranean Sea", true);
		for (int i = 0; i < q3.length; i++) {
			stockQuestionsForExam.addAnswerToQuestionFromStock(q3[i].getAnswer(), q3[i].isItTrueOrFalse(),
					"Which sea is located on the western coast of Israel?");
		}

		stockQuestionsForExam.addOpenQuestionToTheStock(
				"Do you think autonomous vehicles will really help save the world from extinction?",
				"I don't think it can really help save our world because the price of their production is huge ",
				eDifficulty.Hard);

		stockQuestionsForExam.addAmericanQuestionToTheStock("Who was the first Prime Minister of Israel?", 4,
				eDifficulty.Hard);
		AmericanAnswer[] q4 = new AmericanAnswer[5];
		q4[0] = new AmericanAnswer("Shimon Peres", false);
		q4[1] = new AmericanAnswer("Benjamin Netanyahu", false);
		q4[2] = new AmericanAnswer("David Ben-Gurion", true);
		q4[3] = new AmericanAnswer("Golda Meir", false);
		q4[4] = new AmericanAnswer("Benny Gantz", false);
		for (int i = 0; i < q4.length; i++) {
			stockQuestionsForExam.addAnswerToQuestionFromStock(q4[i].getAnswer(), q4[i].isItTrueOrFalse(),
					"Who was the first Prime Minister of Israel?");
		}

		stockQuestionsForExam.addAmericanQuestionToTheStock("Which city is known as the 'Capital of the Negev'?", 4,
				eDifficulty.Easy);
		AmericanAnswer[] q5 = new AmericanAnswer[4];
		q5[0] = new AmericanAnswer("Tel-Aviv", false);
		q5[1] = new AmericanAnswer("Haifa", false);
		q5[2] = new AmericanAnswer("Beersheba", true);
		q5[3] = new AmericanAnswer("Eilat", false);
		for (int i = 0; i < q5.length; i++) {
			stockQuestionsForExam.addAnswerToQuestionFromStock(q5[i].getAnswer(), q5[i].isItTrueOrFalse(),
					"Which city is known as the 'Capital of the Negev'?");
		}

		stockQuestionsForExam.addOpenQuestionToTheStock("What do you think about global warming?",
				"This is a very global event and it is important that the world's population understands the danger in it",
				eDifficulty.Normal);

		// ObjectOutputStream outFile = new ObjectOutputStream(new
		// FileOutputStream(name+".dat"));
		// outFile.writeObject(stockQuestionsForExam);
		// outFile.close();

	}

	public static void addAnswers(AmericanQuestions question, Stock theStock) { // function that helps us to add answers
																				// of the
		// question to exam file
		for (int i = 0; i < question.getNumberOfAnswers(); i++) {
			theStock.addAnswerToQuestionFromStock(question.getAnswersToQuestion()[i].getAnswer(),
					question.getAnswersToQuestion()[i].isItTrueOrFalse(), question.getQuestion());
		}
	}

	public static void printStingToFile(String exam, String fileName) throws IOException { // function that print into
		// the file after our exam
		// is created
		File file = new File(fileName);
		PrintWriter pw = new PrintWriter(file);
		file.createNewFile();
		pw.print(exam);
		pw.print("\n");
		pw.close();
	}

	public static void addQuestionToDataBase(Stock stockQuestions) throws Exception {
		int moreAmericanQuestions;
		int moreOpenQuestions;
		int newSerialCounter;
		if (stockQuestions.getNumOfAllQuestionsInTheStock() > 0) {
			newSerialCounter = stockQuestions
					.getAllquestionsFromtheStock()[stockQuestions.getNumOfAllQuestionsInTheStock() - 1]
					.getSerialNumber();
			stockQuestions.getAllquestionsFromtheStock()[stockQuestions.getNumOfAllQuestionsInTheStock() - 1]
					.setCounter(newSerialCounter);
		}
		System.out.println();
		System.out.println("How many American-questions would you like to add to the Stock ?");
		moreAmericanQuestions = scn.nextInt();
		scn.nextLine();
		if (moreAmericanQuestions > 0) {
			Question[] newQuestionArray = stockQuestions.getAllquestionsFromtheStock();
			newQuestionArray = Arrays.copyOf(newQuestionArray, newQuestionArray.length + moreAmericanQuestions);
			stockQuestions.setNewArrayOfQuestionsToStock(newQuestionArray);
			for (int i = 0; i < moreAmericanQuestions; i++) {
				String question;
				int numOfAnswers;
				String answer;
				boolean trueOrFalse;
				System.out.println("Write new question-->");
				question = scn.nextLine();
				for (int q = 0; q < stockQuestions.getNumOfAllQuestionsInTheStock() - 1; q++) {
					if (question.equals(stockQuestions.getAllquestionsFromtheStock()[q].getQuestion())) {
						System.out.println("This question already exist !");
						return;
					}
				}
				Question.eDifficulty[] allDifficulties = Question.eDifficulty.values();
				for (int t = 0; t < allDifficulties.length; t++) {
					System.out.println((allDifficulties[t].ordinal() + 1) + ") " + allDifficulties[t].name());
				}
				System.out.println("Write difficulty for your question-->");
				Question.eDifficulty theDifficulty = Question.eDifficulty.valueOf(scn.nextLine());
				boolean isValidAnswer = false;
				while (!isValidAnswer) {
					System.out.println("How many answers would be to your question-->");
					numOfAnswers = scn.nextInt();
					scn.nextLine();
					try {
						stockQuestions.addAmericanQuestionToTheStock(question, numOfAnswers, theDifficulty);
						for (int t = 0; t < numOfAnswers; t++) {
							System.out.println("Write " + (t + 1) + "s answer-->");
							answer = scn.nextLine();
							System.out.println("Is it true or false answer-->");
							trueOrFalse = scn.nextBoolean();
							scn.nextLine();
							stockQuestions.addAnswerToQuestionFromStock(answer, trueOrFalse, question);
						}
						isValidAnswer = true;
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
				}

				System.out.println("Your question added successfully !");
				System.out.println();
			}
		}
		System.out.println("How many Open-questions would you like to add to the Stock ?");
		moreOpenQuestions = scn.nextInt();
		scn.nextLine();
		if (moreOpenQuestions > 0) {
			Question[] newQuestionArray = stockQuestions.getAllquestionsFromtheStock();
			newQuestionArray = Arrays.copyOf(newQuestionArray, newQuestionArray.length + moreAmericanQuestions);
			stockQuestions.setNewArrayOfQuestionsToStock(newQuestionArray);
			for (int t = 0; t < moreOpenQuestions; t++) {
				String newOpenQuestion;
				String newAnswer;
				System.out.println("Write new Open-Question --->");
				newOpenQuestion = scn.nextLine();
				System.out.println("Write your answer to the question --->");
				newAnswer = scn.nextLine();
				Question.eDifficulty[] allDifficulties = Question.eDifficulty.values();
				for (int j = 0; j < allDifficulties.length; j++) {
					System.out.println((allDifficulties[j].ordinal() + 1) + ") " + allDifficulties[j].name());
				}
				System.out.println("Write difficulty for your question-->");
				Question.eDifficulty theDifficulty = Question.eDifficulty.valueOf(scn.nextLine());
				if (stockQuestions.addOpenQuestionToTheStock(newOpenQuestion, newAnswer, theDifficulty))
					System.out.println("Your question added successfully !");
				else {
					System.out.println("incorect input");
					t--;
				}
			}
		}
	}

	public static void deleteQuestionFromDataBase(Stock stockQuestions) {
		int userAnswer;
		String deleteQuestion;
		System.out.println(stockQuestions.allQuestionsToString());
		System.out.println("Which question you want to delete , select number-->");
		userAnswer = scn.nextInt();
		if (userAnswer <= stockQuestions.getNumOfAllQuestionsInTheStock()) {
			deleteQuestion = stockQuestions.getAllquestionsFromtheStock()[userAnswer - 1].getQuestion();
			if (!(deleteQuestion == null)) {
				if (stockQuestions.deleteQuestionFromTheStock(deleteQuestion))
					System.out.println("Question successfully deleted");
				return;
			} else {
				System.out.println("Incorect input of values , question did not delete");
			}
		} else {
			System.out.println("This question does not exist !");
		}

	}

	public static void addAnswerToQuestionOnDataBase(Stock stockQuestions) {
		Question questionForAdd;
		int userAnswer;
		System.out.println();
		System.out.println(stockQuestions.allQuestionsToString());
		System.out.println("Which question you would like to add an answer (number)-->");
		userAnswer = scn.nextInt();
		scn.nextLine();
		if (userAnswer <= stockQuestions.getNumOfAllQuestionsInTheStock()) {
			questionForAdd = stockQuestions.getAllquestionsFromtheStock()[userAnswer - 1];
			if (questionForAdd instanceof AmericanQuestions) {
				AmericanQuestions theQuestion1 = (AmericanQuestions) questionForAdd;
				System.out.println(theQuestion1.toString());
				System.out.println(
						"How many answers would you like to add to the question? (max 8 answers to each question) -->");
				int numOfAnswers = scn.nextInt();
				scn.nextLine();
				for (int i = 0; i < numOfAnswers; i++) {
					System.out.println("Enter other answer from the stock (Maximum 8 answers for each question) :");
					System.out.println("Write the answer-->");
					String ans = scn.nextLine();
					System.out.println("Is your answer true or false? ");
					System.out.println("true or false-->");
					boolean boAns = scn.nextBoolean();
					scn.nextLine();
					if (stockQuestions.addAnswerToQuestionFromStock(ans, boAns, theQuestion1.getQuestion())) {
						System.out.println("Answers added successfully !");
					} else {
						System.out.println("Incorect input of values , answer did not add");
					}

				}
			}
			if (questionForAdd instanceof OpenQuestion) {
				OpenQuestion theQuestion2 = (OpenQuestion) questionForAdd;
				System.out.println(theQuestion2.toStringExam());
				System.out.println("Write new answer for Open-question --->");
				String newOpenAns = scn.nextLine();
				if (stockQuestions.switchOpenAnswerToOpenQuestion(theQuestion2.getQuestion(), newOpenAns)) {
					System.out.println("Answers replaced successfully !");
				} else {
					System.out.println("Incorect input of values , answer did not add");
				}
			}

		} else {
			System.out.println("This question does not exist !");
		}
		return;

	}

	public static void deleteAnswerFromQuestionOnDataBase(Stock stockQuestions) throws Exception {
		Question question;
		int userAnswer;
		System.out.println();
		System.out.println(stockQuestions.allQuestionsToString());
		System.out.println("To which question you would like to delete an answer (number)-->");
		userAnswer = scn.nextInt();
		scn.nextLine();
		if (userAnswer <= stockQuestions.getNumOfAllQuestionsInTheStock()) {
			question = stockQuestions.getAllquestionsFromtheStock()[userAnswer - 1];
			if (question instanceof AmericanQuestions) {
				AmericanQuestions theQuestion1 = (AmericanQuestions) question;
				System.out.println(theQuestion1.toString());
				boolean isValidAnswer = false;
				while (!isValidAnswer) {
					System.out.println("How many answers would you like to delete -->");
					int numDeleteAnswers = scn.nextInt();
					scn.nextLine();
					if (theQuestion1.getNumberOfAnswers() - numDeleteAnswers <= 3) {
						System.out.println("Each American-question must have at least 3 answers");
						System.out.println("If you want to stop press 0 ");
					} else {
						for (int z = 0; z < numDeleteAnswers; z++) {
							System.out.println("select " + (z + 1) + "s answer to remove (number)-->");
							userAnswer = scn.nextInt();
							scn.nextLine();
							if (stockQuestions.deleteAnswerFromQuestion(
									theQuestion1.getAnswersToQuestion()[userAnswer - 1].getAnswer(),
									theQuestion1.getQuestion())) {
								System.out.println("Answer successfully deleted");
							} else {
								System.out.println("Incorect input of values , answer did not deleted");
								z--;
							}
						}
						isValidAnswer = true;
					}
				}

			}
			if (question instanceof OpenQuestion) {
				OpenQuestion theQuestion2 = (OpenQuestion) question;
				System.out.println(theQuestion2.toStringExam());
				System.out.println("Write new answer for Open-question --->");
				String newOpenAns = scn.nextLine();
				if (stockQuestions.switchOpenAnswerToOpenQuestion(theQuestion2.getQuestion(), newOpenAns)) {
					System.out.println("Answers replaced successfully !");
				} else {
					System.out.println("Incorect input of values , answer did not add");
				}
			}
		} else {
			System.out.println("This question does not exist !");
		}

	}

	public static void printToFile(Stock exam) throws IOException {
		// After we build our exam here we create the right name for our file
		LocalDateTime time = LocalDateTime.now();
		DateTimeFormatter Cheangform = DateTimeFormatter.ofPattern("yyyy_MM_dd__hh_mm");
		StringBuffer examFile = new StringBuffer("exam_");
		StringBuffer solutionFile = new StringBuffer("solution_");
		examFile.append(time.format(Cheangform));
		solutionFile.append(time.format(Cheangform));
		// System.out.println(exam.toStrong()); //if you want to see the solution result
		// in the console
		printStingToFile(exam.toString(), solutionFile.toString());
		printStingToFile(exam.toStringExam(), examFile.toString());
		System.out.println("Your Exam and Solution file were created successfully please check in the right folder");
		System.out.println();
	}

}
