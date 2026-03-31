package marchvalidation.rules;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;

import static com.intellij.psi.TokenType.BAD_CHARACTER;
import static com.intellij.psi.TokenType.WHITE_SPACE;
import static marchvalidation.psi.ArchRuleTypes.*;

%%

%{
  public _ArchRuleLexer() {
    this((java.io.Reader)null);
  }
%}

%public
%class _ArchRuleLexer
%implements FlexLexer
%function advance
%type IElementType
%unicode

EOL=\R
WHITE_SPACE=\s+

AND=&&|AND|and
OR=\|\||OR|or
IN=IN|in
LITERAL=[a-zA-Z0-9_-]+
WHITE_SPACE=[ \t\n\x0B\f\r]+

%%
<YYINITIAL> {
  {WHITE_SPACE}       { return WHITE_SPACE; }

  "!"                 { return NOT; }
  "=="                { return EQUALS; }
  "!="                { return NOT_EQUALS; }
  "."                 { return DOT; }
  "|"                 { return PIPE; }
  "("                 { return OPEN_PAREN; }
  ")"                 { return CLOSE_PAREN; }
  "NULL"              { return NULL_TOKEN; }

  {AND}               { return AND; }
  {OR}                { return OR; }
  {IN}                { return IN; }
  {LITERAL}           { return LITERAL; }
  {WHITE_SPACE}       { return WHITE_SPACE; }

}

[^] { return BAD_CHARACTER; }
