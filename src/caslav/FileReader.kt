package caslav

import java.io.File

data class Method(
    val name: String,
    val code: String,
    val language: String
)

class FileReader{

    val JAVA_FUNCTION_REGEX = Regex("""(?s)(?<=\b(?:public|private|protected)\s+fun\s+)(\w+)\s*\([^)]*\)\s*\{[^{}]*}""")
    val KOTLIN_FUNCTION_REGEX = Regex("""(?s)fun\s+(\w+)\s*\([^)]*\)\s*(?::[^{]*)?\s*\{[^{}]*}""")

    val SUPPORTED_EXTENSIONS = setOf("java", "kt")

    private val allMethods = mutableListOf<Method>()

    fun readFile(filePath: String): String {
        val file = File(filePath)
        if (!file.exists() || !file.isFile) {
            throw IllegalArgumentException("File $filePath does not exist or is not a regular file")
        }
        return file.readText()
    }

    fun extractMethods(content: String, extension: String) {
        val functionRegex = if (extension == "java") JAVA_FUNCTION_REGEX else KOTLIN_FUNCTION_REGEX

        functionRegex.findAll(content).forEach { result ->
            val functionName = result.groupValues[1]
            val functionCode = result.value
            val functionLanguage = if (extension == "java") "Java" else "Kotlin"
            allMethods.add(Method(name = functionName, code = functionCode, language = functionLanguage))
        }
    }

    fun extractMethodsFromFile(filePath: String){
        val content = readFile(filePath)
        val extension = File(filePath).extension
        extractMethods(content, extension)
    }

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
    val rootDirectory = System.getProperty("user.dir")
    val directoryPath = "$rootDirectory/data/"

    val fileReader: FileReader = FileReader()
    fileReader.extractAllMethodsFromDirectory(directoryPath)
    fileReader.printAllMethods()

}
