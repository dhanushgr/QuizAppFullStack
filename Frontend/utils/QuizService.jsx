
export const api = axios.create({
    baseURL: "http://localhost:9192/api/quiz"
})

export const createQuestion = async(quizQuestion) =>{
    try {
        const response = await api.post("/create-new-question", quizQuestion)
        return response.data
    } catch (error) {
        console.error(error)
    }
}

export const getAllQuestions = async() =>{
    try {
        const response = await api.get("/all-questions")
        return response.data
    } catch (error) {
        console.error(error)
        return []
    }
}

export const getQuestionsForUser = async(number, subject) =>{
    try {
        const response = await api.get(`/fetch-questions-for-user?numOfQuestion=${number}&subject=${subject}`)
        return response.data
    } catch (error) {
        console.error(error)
        return []
    }
}

export const getAllSubjects = async() =>{
    try {
        const response = await api.get("/subjects")
        return response.data
    } catch (error) {
        console.error(error)
    }
}
