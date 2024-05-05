package caslav

// Data class to hold evaluation results for each method
data class Evaluation(
    val method: Method,
    var complexity: Int = 0,
    var cognitiveComplexity: Double = .0,
    var namingConvention: Boolean = false,
)

// Class responsible for evaluating code complexity and style
class Evaluator {

    private val evaluations = mutableListOf<Evaluation>() // List to hold evaluation results
    private val methods = MethodsExtractor() // Instance of MethodsExtractor to extract methods from files
    private var namingConventionPercentage: Double = 0.0 // Percentage of methods adhering to naming convention

    // Set the directory for evaluation and extract methods from files
    fun setEvaluationDirectory(evaluationPath: String) {
        methods.emptyMethodList()
        methods.extractAllMethodsFromDirectory(evaluationPath)
        methods.getMethodList().forEach { method ->
            evaluations.add(Evaluation(method))
        }
    }

    // Evaluate code complexity for each method
    fun evaluateComplexity() {
        evaluations.forEach { evaluation ->
            evaluateCodeComplexity(evaluation)
        }
    }

    // Evaluate code style (naming convention) for each method
    fun evaluateCodeStyle() {
        namingConventionPercentage = 0.0
        evaluations.forEach { evaluation ->
            evaluation.namingConvention = isCamelCase(evaluation.method.name) // Check if method names are camelCase
            namingConventionPercentage += if (evaluation.namingConvention) 1.0 else 0.0
        }
        if (evaluations.isNotEmpty())
            namingConventionPercentage /= evaluations.count() // Calculate percentage of methods with camelCase names
        namingConventionPercentage *= 100 // Convert to percentage
    }

    // Evaluate code complexity for a single method
    fun evaluateCodeComplexity(evaluation: Evaluation) {
        for (line in evaluation.method.code.split("\n")) {
            val words = line.split(" ")
            for (word in words) {
                if (word.startsWith("||") || word.startsWith("&&") || word.startsWith("==") || word.startsWith("!=")
                    || word.startsWith("for")
                    || word.contains(".forEach")
                    || word.startsWith("while") || word.startsWith("if")
                ) {
                    evaluation.complexity++
                }
            }
            evaluation.complexity += countOccurrences(line, "->")
            evaluation.complexity += countOccurrences(line, "case")
        }
    }

    // Check if a string follows camelCase naming convention
    fun isCamelCase(str: String): Boolean {
        // If PascalCase return false
        if (str.isEmpty() || str[0].isUpperCase()) {
            return false
        }

        // If _PascalCase return false
        if (str.length > 1 && str[0] == '_' && str[1].isUpperCase())
            return false

        // If snake_case
        for (i in 1 until str.length) {
            if (str[i] == '_')
                return false
        }

        // All characters meet the camelCase convention
        return true
    }

    // Print top 3 methods with highest complexity values
    fun printTop3Evaluations() {
        println("Top 3 Methods with highest complexity values:")
        evaluations.sortedByDescending { it.complexity }.take(3).forEachIndexed { index, evaluation ->
            println("#${index + 1}")
            println("Method: ${evaluation.method.name}")
            println("Complexity: ${evaluation.complexity}")
            println("NamingConvention (camelCase): ${evaluation.namingConvention}")
            println("Code:\n${evaluation.method.code}")
            println()
        }
    }

    // Print percentage of methods adhering to naming convention
    fun printNameConventionPercentage() {
        println("There is ${namingConventionPercentage}% of total ${evaluations.count()} methods that follow camelCase naming convention")
    }

    // Helper function to count occurrences of a substring in a string
    fun countOccurrences(text: String, substr: String): Int {
        val regex = Regex("""\Q$substr\E""")
        return regex.findAll(text).count()
    }

}

// Main function to execute code evaluation tasks
fun main() {
    // Get the root directory of the project
    val rootDirectory = System.getProperty("user.dir")
    // Specify the directory containing the files to analyze
    val directoryPath = "$rootDirectory/data/"

    val evaluator = Evaluator()
    evaluator.setEvaluationDirectory(directoryPath)
    evaluator.evaluateComplexity()
    evaluator.evaluateCodeStyle()

    println("Task 1:")
    println()
    evaluator.printTop3Evaluations()
    println("------------------------------------")
    println()
    println("Task 2:")
    println()
    evaluator.printNameConventionPercentage()
}
