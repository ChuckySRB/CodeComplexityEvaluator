package caslav

data class Evaluation(
    val method: Method,
    var complexity: Int = 0,
    var cognitiveComplexity: Double = .0,
    var namingConvention: Boolean = false,
)

class Evaluator() {

    private val evaluations = mutableListOf<Evaluation>()
    private val methods = MethodsExtractor()

    fun setEvaluationDirectory(evaluationPath: String) {
        methods.emptyMethodList()
        methods.extractAllMethodsFromDirectory(evaluationPath)
        methods.getMethodList().forEach({ method -> evaluations.add(Evaluation(method)) })
    }

    fun evaluateComplexity() {
        evaluations.forEach { evaluation ->
            evaluation.method.code.split(" ").forEach { word ->
                if (word.startsWith("if")) {
                    evaluation.complexity++
                }
            }
            // Implement your code complexity evaluation logic here
        }
    }

        // Function to evaluate code style
    fun evaluateCodeStyle() {
        val evaluations = mutableListOf<Evaluation>()
        // Implement your code style evaluation logic here

    }

    fun printEvaluations() {
        evaluations.forEach { evaluation ->
            if (evaluation.complexity > 0) {
                println("Method: ${evaluation.method.name}")
                println("Complexity: ${evaluation.complexity}")
                println("CognitiveComplexity: ${evaluation.cognitiveComplexity}")
                println("NamingConvention: ${evaluation.namingConvention}")
            }
        }
    }

}

fun main() {
    // Get the root directory of the project
    val rootDirectory = System.getProperty("user.dir")
    // Specify the directory containing the files to analyze
    val directoryPath = "$rootDirectory/data/"

    val evaluator = Evaluator()
    evaluator.setEvaluationDirectory(directoryPath)
    evaluator.evaluateComplexity()
    evaluator.printEvaluations()
}

