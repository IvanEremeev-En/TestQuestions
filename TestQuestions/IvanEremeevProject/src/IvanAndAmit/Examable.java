package IvanAndAmit;

import java.io.IOException;

public interface Examable {
	Stock createManualExam(Stock stockOfQuestions ) throws IOException, Exception;
	Stock createAutoMaticExam(Stock stockOfQuestions, int numOfQuestions )throws IOException, Exception;
}
