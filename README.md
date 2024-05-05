# Code Complexity Evaluator

This project is a simple Kotlin application designed to evaluate code complexity and style within a set of Java and Kotlin files. It analyzes the methods/functions in the codebase and provides insights into their complexity and adherence to coding conventions.

## Features

- **Code Complexity Evaluation**: The application analyzes the methods/functions in the provided Java and Kotlin files and computes their complexity. The complexity is just the number of (`if`, `for`, `while`, etc.) and boolean operations (`||`, `&&`) in the code.
The simple complexity was calculated for the purpose of fulfilling the JetBrains task requirements, and was also inspired by [SonarQube](https://docs.sonarsource.com/sonarqube/latest/user-guide/metric-definitions/) complexity metrics that does the same thing. 
There is a field called cognitiveComplexity that is not calculated and is left for future complexity development (also inspired by [SonarQube](https://docs.sonarsource.com/sonarqube/latest/user-guide/metric-definitions/) ) It is supposed to calculate linear complexity for nested loops and do more calculation other then just counting them.

- **Code Style Check**: The application performs a simple code style check by ensuring that method names follow a naming convention (camelCase).

- **Top Methods Analysis**: It identifies the top three methods with the highest complexity scores and provides details about their complexity, naming convention adherence, and code snippets.

- **Naming Convention Percentage**: It calculates the percentage of methods adhering to the camelCase naming convention.

## Usage

1. **Setup**:
    - Clone this repository to your local machine.
    - Ensure you have Kotlin installed.

2. **Input Files**:
    - Place the Java and Kotlin files you want to analyze in the `data` directory at the root of the project.
    - There are 3 projects that I already added for example use: Farming-App(open-source android app), 
   Kotlin (algorithms library for Kotlin) and PPprojekat23 (Compiler done in Java for my faculty project) 

3. **Run the Application**:
    - Run the Kotlin file src.caslav.Evaluator.kt to get the results for the Tasks required
    - (optional) Run the src.caslav.MethodsExtractor.kt to see all the functions that Extractor is pulling out of the data folder
     
4. **View Results in terminal**:
    - The application will display the top three methods with the highest complexity scores, along with details about their complexity, naming convention adherence, and code snippets.
    - It will also show the percentage of methods adhering to the camelCase naming convention.
5. **Experiment**:
   - Change parameters in the fun main in the Evaluator.kt file
   - Put your code in data folder
   - Call functions as they are described

## Example Results

The code output for just Kotlin algorithms library:
```
Task 1:

Top 3 Methods with highest complexity values:
#1
Method: interpolationSearch
Complexity: 6
NamingConvention (camelCase): true
Code:

    val pos: Int

    if (lo <= hi && x >= arr[lo] && x <= arr[hi]) {

        pos = (lo
                + ((hi - lo) / (arr[hi] - arr[lo])
                * (x - arr[lo])))

        if (arr[pos] == x) return pos

        if (arr[pos] < x) return interpolationSearch(arr, pos + 1, hi,
                x)

        if (arr[pos] > x) return interpolationSearch(arr, lo, pos - 1,
                x)
    }
    return -1


#2
Method: min
Complexity: 4
NamingConvention (camelCase): true
Code:

    if (x <= y && x <= z) return x
    return if (y <= x && y <= z) y else z


#3
Method: getFactorial
Complexity: 4
NamingConvention (camelCase): true
Code:

    if (number < 0L) {
        throw InvalidParameterException("The number of which to calculate the factorial must be greater or equal to zero.")
    } else return when (number) {
        0L -> 1
        1L -> number
        else -> number * getFactorial(number - 1)
    }


------------------------------------

Task 2:

There is 98.9010989010989% of total 91 methods that follow camelCase naming convention
```

The code output for all example projects that I provided gives the following output:
```
Task 1:

Top 3 Methods with highest complexity values:
#1
Method: visit
Complexity: 7
NamingConvention (camelCase): true
Code:

		//TODO
		SyntaxNode parent = desiOnlyIdent.getParent();
		if(AssignDesignatorStmt.class != parent.getClass() &&
				ListDesiStartWithDesi.class != parent.getClass() &&
				AdditionalDesiWithDesi.class != parent.getClass() &&
				DesiStatementArrayAssign.class != parent.getClass() &&
				ReadStatement.class != parent.getClass() &&
				desiOnlyIdent.obj.getKind() != Obj.Meth) {
			Code.load(desiOnlyIdent.obj);
		} else if (ListDesiStartWithDesi.class == parent.getClass()) {
			arrAssignStack.push(new ArrAssignInd(desiOnlyIdent.obj, arrayAssignCnt));
		} else if(AdditionalDesiWithDesi.class == parent.getClass()) {
			arrayAssignCnt++;
			arrAssignStack.push(new ArrAssignInd(desiOnlyIdent.obj, arrayAssignCnt));
		
		}
	

#2
Method: visit
Complexity: 7
NamingConvention (camelCase): true
Code:

		if(validScopeOpened && !already_defined(formParamArray.getParamName(), formParamArray.getLine())) {
			Obj obj = Tab.insert(Obj.Var, formParamArray.getParamName(), new Struct(Struct.Array, formParamArray.getType().struct));
			obj.setFpPos(fpCounter++);
			report_info("Deklarisan formalni argument \"" + formParamArray.getParamName() + "\" na liniji "
					+ formParamArray.getLine(), null, obj);
		}
	

#3
Method: visit
Complexity: 7
NamingConvention (camelCase): true
Code:

		if(validScopeOpened && !already_defined(formParamNoArray.getParamName(), formParamNoArray.getLine())) {
			Obj obj = Tab.insert(Obj.Var, formParamNoArray.getParamName(), formParamNoArray.getType().struct);
			obj.setFpPos(fpCounter++);
			report_info("Deklarisan formalni argument \"" + formParamNoArray.getParamName() + "\" na liniji "
					+ formParamNoArray.getLine(), null, obj);
		}
	

------------------------------------

Task 2:

There is 92.37385321100918% of total 1744 methods that follow camelCase naming convention
```

