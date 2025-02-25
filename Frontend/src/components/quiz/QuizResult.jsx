import React from "react";
import { useLocation, useNavigate } from "react-router-dom";

const QuizResult = () => {
    const location = useLocation();
    const navigate = useNavigate();
    const { quizQuestions, totalScores, selectedSubject, selectedNumQuestions } = location.state || {};
    const numQuestions = quizQuestions?.length || 0;
    const percentage = Math.round((totalScores / numQuestions) * 100);

    const handleRetakeQuiz = () => {
        // Navigate back to the quiz page with the same selected subject and number of questions
        navigate("/take-quiz", { state: { selectedSubject, selectedNumQuestions } });
    };

    return (
        <section className="container mt-5">
            <h3>Your Quiz Result Summary</h3>
            <hr />
            <h5 className="text-info">
                You answered {totalScores} out of {numQuestions} questions correctly.
            </h5>
            <p>Your total score is {percentage}%.</p>

            <button className="btn btn-primary btn-sm" onClick={handleRetakeQuiz}>
                Retake this quiz
            </button>
        </section>
    );
};

export default QuizResult;