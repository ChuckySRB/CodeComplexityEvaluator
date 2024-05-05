package caslav

import java.util.Stack

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
            evaluateCodeComplexity(evaluation)
        }
    }

    // Function to evaluate code style
    fun evaluateCodeStyle() {
        evaluations.forEach { evaluation ->
            evaluation.namingConvention = isCamelCase(evaluation.method.name) // Check if method names are camelCase
        }
    }

    fun countOccurrences(text: String, substr: String): Int {
        val regex = Regex("""\Q$substr\E""")
        return regex.findAll(text).count()
    }

    fun evaluateCodeComplexity(evaluation: Evaluation){
        for (line in evaluation.method.code.split("\n")) {
            val words = line.split(" ")
            for(word in words){
                if (word.startsWith("||") || word.startsWith("&&") || word.startsWith("==") || word.startsWith("!=")
                    || word.startsWith("for")
                    || word.contains(".forEach")
                    || word.startsWith("while") || word.startsWith("if")){
                        evaluation.complexity++
                }
            }
            evaluation.complexity+= countOccurrences(line, "->")
            evaluation.complexity+= countOccurrences(line, "case")
        }
    }
    fun evaluateJavaCodeComplexity(evaluation: Evaluation){

    }

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

    fun printTop3Evaluations(){
        evaluations.sortedByDescending { it.complexity }.take(3).forEachIndexed{index, evaluation->
            println("#${index+1}")
            println("Method: ${evaluation.method.name}")
            println("Complexity: ${evaluation.complexity}")
            println("NamingConvention (camelCase): ${evaluation.namingConvention}")
            println("Code: ${evaluation.method.code}")
            println()
        }
    }

    fun printEvaluations() {
        evaluations.forEach { evaluation ->
            if (!evaluation.namingConvention) {
                println("Method: ${evaluation.method.name}")
                println("Complexity: ${evaluation.complexity}")
                println("CognitiveComplexity: ${evaluation.cognitiveComplexity}")
                println("NamingConvention: ${evaluation.namingConvention}")
                println()
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
    evaluator.evaluateCodeStyle()
    evaluator.printTop3Evaluations()
}

