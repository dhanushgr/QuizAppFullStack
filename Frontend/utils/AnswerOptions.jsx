import React from "react";

const AnswerOptions = ({ question, isChecked, handleAnswerChange, handleCheckboxChange }) => {
	if (!question || !question.choices || question.choices.length === 0) {
		return (
			<div>
				No answer options available. <br /> Try again by selecting fewer questions.
			</div>
		);
	}

	const { id, questionType, choices } = question;
	const isSingleAnswer = questionType === "SINGLE_ANSWER";
	const isMultipleAnswer = questionType === "MULTIPLE_ANSWER";

	return (
		<div>
			{choices.map((choice, index) => (
				<div key={choice} className="form-check mb-3">
					<input
						className="form-check-input"
						type={isSingleAnswer ? "radio" : "checkbox"}
						id={`${id}-${index}`} // Unique ID for each option
						name={`question-${id}`}
						value={choice}
						checked={isChecked(id, choice)}
						onChange={() =>
							isSingleAnswer
								? handleAnswerChange(id, choice)
								: handleCheckboxChange(id, choice)
						}
					/>
					<label htmlFor={`${id}-${index}`} className="form-check-label ms-2">
						{choice}
					</label>
				</div>
			))}
		</div>
	);
};

export default AnswerOptions;
