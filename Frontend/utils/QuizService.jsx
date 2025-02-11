import axios from "axios"

export const api = axios.create({
	baseURL: `${import.meta.env.VITE_API_URL}/api/quiz`
})

export const createQuestion = async (quizQuestion) => {
  try {
    const response = await api.post("/questions", quizQuestion);
    return response.data;
  } catch (error) {
    console.error("Error creating question:", error.response?.data || error.message);
    throw error; // Re-throw the error to handle it in the component
  }
};

export const getAllQuestions = async() =>{
  try {
    const response = await api.get("/questions")
    return response.data
  } catch (error) {
    console.error(error)
    return []
  }
}

export const fetchQuizForUser = async(number, subject) =>{
  try {
    const response = await api.get(
			`/questions/random?numOfQuestions=${number}&subjects=${subject}`
		)
    return response.data
  } catch (error) {
    console.error(error)
    return []
  }
}

export const getSubjects = async() =>{
  try {
    const response = await api.get("/subjects")
    return response.data
  } catch (error) {
    console.error(error)
  }
}

export const updateQuestion = async(id, question) =>{
  try {
    const response = await api.put(`/questions/${id}`, question)
    return response.data
  } catch (error) {
    console.error(error)
  }
}

export const getQuestionById = async(id) =>{
  try {
    const response = await api.get(`/questions/${id}`)
		return response.data
  } catch (error) {
    console.error(error)
  }
}

export const deleteQuestion = async(id) =>{
  try {
    const response = await api.delete(`/questions/${id}`)
		return response.data
  } catch (error) {
    console.error(error)
  }
}