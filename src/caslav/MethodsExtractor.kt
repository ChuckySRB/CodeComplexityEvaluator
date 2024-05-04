package caslav

import java.io.File

data class Method(
    val name: String,
    val code: String,
    val language: String
)

class MethodsExtractor {

    // Regular expressions for extracting method/function declarations
    private val JAVA_FUNCTION_REGEX = Regex("""(?s)(?<=\b(?:public|private|protected)\s+fun\s+)(\w+)\s*\([^)]*\)\s*\{([^{}]*(?:\{[^{}]*}[^{}]*)*)}""")
    private val KOTLIN_FUNCTION_REGEX = Regex("""(?s)fun\s+(\w+)\s*\([^)]*\)\s*(?::[^{]*)?\s*\{([^{}]*(?:\{[^{}]*}[^{}]*)*)}""")



    // Supported file extensions
    private val SUPPORTED_EXTENSIONS = setOf("java", "kt")

    // List to store all extracted methods
    private val allMethods = mutableListOf<Method>()

    // Function to read content of a file
    private fun readFile(filePath: String): String {
        val file = File(filePath)
        if (!file.exists() || !file.isFile) {
            throw IllegalArgumentException("File $filePath does not exist or is not a regular file")
        }
        return file.readText()
    }

    // Function to extract methods from content based on file extension
    private fun extractMethods(content: String, extension: String) {
        val functionRegex = if (extension == "java") JAVA_FUNCTION_REGEX else KOTLIN_FUNCTION_REGEX
        functionRegex.findAll(content).forEach { result ->
            val functionName = result.groupValues[1]
            val functionCode = result.groupValues[2]
            val functionLanguage = if (extension == "java") "Java" else "Kotlin"
            allMethods.add(Method(name = functionName, code = functionCode, language = functionLanguage))
        }
    }

    // Function to extract methods from a file
    fun extractMethodsFromFile(filePath: String){
        val content = readFile(filePath)
        val extension = File(filePath).extension
        extractMethods(content, extension)
    }

    // Function to extract methods from all files in a directory and its subdirectories
    fun extractAllMethodsFromDirectory(directoryPath: String){
        val files = mutableListOf<File>()
        File(directoryPath).walkTopDown().forEach { file ->
            if (file.isFile && file.extension in SUPPORTED_EXTENSIONS) {
                files.add(file)
            }
        }
        if (files.isNullOrEmpty()) {
            return
        }
        files.forEach { file -> extractMethodsFromFile(file.absolutePath) }
    }

    fun emptyMethodList(){
        allMethods.clear()
    }

    fun getMethodList(): List<Method>{
        return allMethods
    }

    // Function to print all extracted methods
    fun printAllMethods() {
        println("All Methods List: [")
        allMethods.forEachIndexed { index, method ->
            println("Method ${index + 1}:")
            println("Name: ${method.name}")
            println("Language: ${method.language}")
            println("Code:")
            println(method.code)
            println()
        }
        println("]")
    }

}

fun main() {
    // Get the root directory of the project
    val rootDirectory = System.getProperty("user.dir")
    // Specify the directory containing the files to analyze
    val directoryPath = "$rootDirectory/data/"

    // Create an instance of MethodsExtractor
    val methodsExtractor = MethodsExtractor()
    // Extract methods from all files in the directory
    methodsExtractor.extractAllMethodsFromDirectory(directoryPath)
    // Print all extracted methods
    methodsExtractor.printAllMethods()

}
