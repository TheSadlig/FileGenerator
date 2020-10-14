grammar Filegenerator;

/* LEXICAL RULES */
LSQUAREBRACKET : '[';
RSQUAREBRACKET : ']';

LCURLYBRACKET : '{';
RCURLYBRACKET : '}';

LANGLEBRACKET : '<';
RANGLEBRACKET : '>';

LPARENTHESIS : '(';
RPARENTHESIS : ')';

PIPE : '|';

EQUALSIGN : '=';

SEPARATOR : ',';

DOLLARSIGN: '$';

SPACE: ' ';

DECIMAL : '-'?[0-9]+('.'[0-9]+)? ;

IDENTIFIER : [a-zA-Z_/.][a-zA-Z_0-9-/%:\.]+ ;

VARIABLE: DOLLARSIGN IDENTIFIER;

END : '[[[END]]]';
END_CHUNK : '===END===';

WS  :   ('\r'|'\n'|'\t')+?;
ANY : .+?;




/* PARSER RULES */

/* This will be the entry point of our parser. */
eval :
         file_generator_element* EOF;

file_generator_element
    :   action
    |   display
    |   loop
    |   raw_text
    |   chunk
    |   include_file
    ;

action
    : LCURLYBRACKET LCURLYBRACKET LCURLYBRACKET parameters RCURLYBRACKET RCURLYBRACKET RCURLYBRACKET
    ;

display
    : LANGLEBRACKET LANGLEBRACKET LANGLEBRACKET parameters RANGLEBRACKET RANGLEBRACKET RANGLEBRACKET
    ;

loop
    : LSQUAREBRACKET LSQUAREBRACKET LSQUAREBRACKET parameters RSQUAREBRACKET RSQUAREBRACKET RSQUAREBRACKET WS? file_generator_element* WS? END WS?
    ;

nested_function
    : LPARENTHESIS parameters RPARENTHESIS
    ;

// Allows to split the input file into several output ones
chunk
    : EQUALSIGN EQUALSIGN EQUALSIGN parameters EQUALSIGN EQUALSIGN EQUALSIGN WS? file_generator_element* END_CHUNK WS?
    ;

include_file
    : PIPE  PIPE  PIPE  parameters PIPE  PIPE  PIPE
    ;

value_or_array
    : array
    | value
    | nested_function
    ;

array
   : VARIABLE LSQUAREBRACKET array_index RSQUAREBRACKET
   ;

array_index
    : value
    ;

value
    : VARIABLE
    | IDENTIFIER
    | SPACE
    | DECIMAL
    ;

parameters
      : value_or_array ( SEPARATOR value_or_array ) *
      ;

raw_text
    : ANY
    | DECIMAL
    | IDENTIFIER
    | WS
    | SPACE
    | SEPARATOR
    | LSQUAREBRACKET
    | RSQUAREBRACKET 
    | LCURLYBRACKET 
    | RCURLYBRACKET 
    | LANGLEBRACKET
    | RANGLEBRACKET
    | EQUALSIGN
    | DOLLARSIGN
    ;
