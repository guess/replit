package com.stevestrates.replit.ui.editor

import android.content.Context
import com.amrdeveloper.codeview.CodeView
import com.stevestrates.replit.R
import java.util.regex.Pattern

/**
 * Python syntax taken from:
 * https://github.com/AmrDeveloper/CodeView/blob/master/app/src/main/java/com/amrdeveloper/codeviewlibrary/syntax/PythonLanguage.java
 */
object PythonLanguage {
    //Language Keywords
    private val PATTERN_KEYWORDS = Pattern.compile(
        "\\b(False|await|else|import|pass|None|break|except|in|raise" +
                "|True|class|finally|is|return|and|continue|for|lambda" +
                "|try|as|def|from|nonlocal|while|assert|del|global|not" +
                "|with|async|elif|if|or|yield)\\b"
    )

    //Brackets and Colons
    private val PATTERN_BUILTINS = Pattern.compile("[,:;[->]{}()]")

    //Data
    private val PATTERN_NUMBERS = Pattern.compile("\\b(\\d*[.]?\\d+)\\b")
    private val PATTERN_CHAR = Pattern.compile("'[a-zA-Z]'")
    private val PATTERN_STRING = Pattern.compile("\".*\"")
    private val PATTERN_HEX = Pattern.compile("0x[0-9a-fA-F]+")
    private val PATTERN_TODO_COMMENT = Pattern.compile("#TODO[^\n]*")
    private val PATTERN_ATTRIBUTE = Pattern.compile("\\.[a-zA-Z0-9_]+")
    private val PATTERN_OPERATION =
        Pattern.compile(":|==|>|<|!=|>=|<=|->|=|>|<|%|-|-=|%=|\\+|\\-|\\-=|\\+=|\\^|\\&|\\|::|\\?|\\*")
    private val PATTERN_HASH_COMMENT = Pattern.compile("#(?!TODO )[^\\n]*")

    fun applyMonokaiTheme(context: Context, codeView: CodeView) {
        codeView.resetSyntaxPatternList()
        codeView.resetHighlighter()
        val resources = context.resources

        //View Background
        codeView.setBackgroundColor(codeView.resources.getColor(R.color.monokia_pro_black))

        //Syntax Colors
        codeView.addSyntaxPattern(
            PATTERN_HEX,
            context.resources.getColor(R.color.monokia_pro_purple)
        )
        codeView.addSyntaxPattern(
            PATTERN_CHAR,
            context.resources.getColor(R.color.monokia_pro_green)
        )
        codeView.addSyntaxPattern(
            PATTERN_STRING,
            context.resources.getColor(R.color.monokia_pro_orange)
        )
        codeView.addSyntaxPattern(
            PATTERN_NUMBERS,
            context.resources.getColor(R.color.monokia_pro_purple)
        )
        codeView.addSyntaxPattern(
            PATTERN_KEYWORDS,
            context.resources.getColor(R.color.monokia_pro_pink)
        )
        codeView.addSyntaxPattern(
            PATTERN_BUILTINS,
            context.resources.getColor(R.color.monokia_pro_white)
        )
        codeView.addSyntaxPattern(
            PATTERN_HASH_COMMENT,
            context.resources.getColor(R.color.monokia_pro_grey)
        )
        codeView.addSyntaxPattern(
            PATTERN_ATTRIBUTE,
            context.resources.getColor(R.color.monokia_pro_sky)
        )
        codeView.addSyntaxPattern(
            PATTERN_OPERATION,
            context.resources.getColor(R.color.monokia_pro_pink)
        )
        //Default Color
        codeView.setTextColor(context.resources.getColor(R.color.monokia_pro_white))
        codeView.addSyntaxPattern(PATTERN_TODO_COMMENT, context.resources.getColor(R.color.gold))
        codeView.reHighlightSyntax()
    }

    val indentationStarts: Set<Char>
        get() {
            val characterSet: MutableSet<Char> = HashSet()
            characterSet.add(':')
            return characterSet
        }

    val indentationEnds: Set<Char>
        get() = HashSet()
}
