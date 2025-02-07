import React, { useEffect, useState } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import { fetchQuizForUser } from "../../../utils/QuizService";
import AnswerOptions from "../../../utils/AnswerOptions";

const Quiz = () => {
	const [quizQuestions, setQuizQuestions] = useState([]);
	const [selectedAnswers, setSelectedAnswers] = useState([]);
	const [currentQuestionIndex, setCurrentQuestionIndex] = useState(0);
	const [totalScores, setTotalScores] = useState(0);
	const location = useLocation();
	const navigate = useNavigate();
	const { selectedSubject, selectedNumQuestions } = location.state || {};

	useEffect(() => {
		fetchQuizData();
	}, []);

	const fetchQuizData = async () => {
		if (selectedNumQuestions && selectedSubject) {
			const questions = await fetchQuizForUser(selectedNumQuestions, selectedSubject);
			setQuizQuestions(questions);
		}
	};

	const handleAnswerChange = (questionId, answer) => {
		setSelectedAnswers((prevAnswers) => {
			const existingAnswerIndex = prevAnswers.findIndex((ans) => ans.id === questionId);
			const selectedAnswer = answer.charAt(0);

			if (existingAnswerIndex !== -1) {
				const updatedAnswers = [...prevAnswers];
				updatedAnswers[existingAnswerIndex] = { id: questionId, answer: selectedAnswer };
				return updatedAnswers;
			} else {
				return [...prevAnswers, { id: questionId, answer: selectedAnswer }];
			}
		});
	};

	const handleCheckboxChange = (questionId, choice) => {
		setSelectedAnswers((prevAnswers) => {
			const existingAnswerIndex = prevAnswers.findIndex((ans) => ans.id === questionId);
			const selectedOption = choice.charAt(0);
	
			if (existingAnswerIndex !== -1) {
				const updatedAnswers = [...prevAnswers];
				let selectedChoices = updatedAnswers[existingAnswerIndex].answer;
	
				if (!Array.isArray(selectedChoices)) {
					selectedChoices = [];
				}
	
				if (selectedChoices.includes(selectedOption)) {
					selectedChoices = selectedChoices.filter((opt) => opt !== selectedOption);
				} else {
					selectedChoices = [...selectedChoices, selectedOption];
				}
	
				updatedAnswers[existingAnswerIndex] = { id: questionId, answer: selectedChoices };
				return updatedAnswers;
			} else {
				return [...prevAnswers, { id: questionId, answer: [selectedOption] }];
			}
		});
	};
	

	const isChecked = (questionId, choice) => {
		const selectedAnswer = selectedAnswers.find((answer) => answer.id === questionId);
		if (!selectedAnswer) return false;
		if (Array.isArray(selectedAnswer.answer)) {
			return selectedAnswer.answer.includes(choice.charAt(0));
		}
		return selectedAnswer.answer === choice.charAt(0);
	};

	const handleSubmit = () => {
		let scores = 0;
		quizQuestions.forEach((question) => {
			const selectedAnswer = selectedAnswers.find((answer) => answer.id === question.id);
			if (selectedAnswer) {
				const selectedOptions = Array.isArray(selectedAnswer.answer)
					? selectedAnswer.answer.map((opt) => opt.charAt(0))
					: [selectedAnswer.answer.charAt(0)];
				const correctOptions = Array.isArray(question.correctAnswers)
					? question.correctAnswers.map((opt) => opt.charAt(0))
					: [question.correctAnswers.charAt(0)];
	
				const isCorrect =
					selectedOptions.length === correctOptions.length &&
					selectedOptions.every((opt) => correctOptions.includes(opt));
	
				if (isCorrect) {
					scores++;
				}
			}
		});
		setTotalScores(scores);
		setSelectedAnswers([]);
		setCurrentQuestionIndex(0);
		navigate("/quiz-result", { 
			state: { 
				quizQuestions, 
				totalScores: scores, 
				selectedSubject, 
				selectedNumQuestions 
			} 
		});
	};

	const handleNextQuestion = () => {
		if (currentQuestionIndex < quizQuestions.length - 1) {
			setCurrentQuestionIndex((prevIndex) => prevIndex + 1);
		} else {
			handleSubmit();
		}
	};

	const handlePreviousQuestion = () => {
		if (currentQuestionIndex > 0) {
			setCurrentQuestionIndex((prevIndex) => prevIndex - 1);
		}
	};

	return (
		<div className="p-5">
			<h3 className="text-info">
				Question {quizQuestions.length > 0 ? currentQuestionIndex + 1 : 0} of {quizQuestions.length}
			</h3>

			<h4 className="mb-4">
				<pre>{quizQuestions[currentQuestionIndex]?.question}</pre>
			</h4>

			<AnswerOptions
				question={quizQuestions[currentQuestionIndex]}
				isChecked={isChecked}
				handleAnswerChange={handleAnswerChange}
				handleCheckboxChange={handleCheckboxChange}
			/>

			<div className="mt-4">
				<button
					className="btn btn-sm btn-primary me-2"
					onClick={handlePreviousQuestion}
					disabled={currentQuestionIndex === 0}
				>
					Previous Question
				</button>
				<button
					className={`btn btn-sm ${currentQuestionIndex === quizQuestions.length - 1 ? "btn-warning" : "btn-info"}`}
					onClick={handleNextQuestion}
					disabled={
						!selectedAnswers.find((answer) => answer.id === quizQuestions[currentQuestionIndex]?.id)
					}
				>
					{currentQuestionIndex === quizQuestions.length - 1 ? "Submit Quiz" : "Next Question"}
				</button>
			</div>
		</div>
	);
};

export default Quiz;
