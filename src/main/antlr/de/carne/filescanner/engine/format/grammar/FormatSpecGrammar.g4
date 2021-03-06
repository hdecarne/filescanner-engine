/*
 * Copyright (c) 2007-2021 Holger de Carne and contributors, All Rights Reserved.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
grammar FormatSpecGrammar;

@header {
package de.carne.filescanner.engine.format.grammar;
}

// Tokens

ByteSymbols: 'byte_symbols';
WordSymbols: 'word_symbols';
DWordSymbols: 'dword_symbols';
QWordSymbols: 'qword_symbols';

ByteFlagSymbols: 'byte_flag_symbols';
WordFlagSymbols: 'word_flag_symbols';
DWordFlagSymbols: 'dword_flag_symbols';
QWordFlagSymbols: 'qword_flag_symbols';

FormatSpec: 'format_spec';
Struct: 'struct';
Array: 'array';
Sequence: 'sequence';
Union: 'union';
Scan: 'scan';
Range: 'range';
Conditional: 'conditional';
Encoded: 'encoded';
DecodeAt: 'decode_at';
Validate: 'validate';
Text: 'text';
Format: 'format';
Renderer: 'renderer';
Link: 'link';
Export: 'export';
LittleEndian: 'littleEndian';
BigEndian: 'bigEndian';
StopBefore: 'stopBefore';
StopAfter: 'stopAfter';
Min: 'min';
Max: 'max';
Size: 'size';
Charset: 'charset';

Byte: 'byte';
Word: 'word';
DWord: 'dword';
QWord: 'qword';
Char: 'char';
String: 'string';

Apply: '->';
LBracket: '(';
RBracket: ')';
LCBracket: '{';
RCBracket: '}';
LSBracket: '[';
RSBracket: ']';
Comma: ',';
Colon: ':';
Or: '||';
At: '@';
Hash: '#';
Ellipsis: '...';

Number: DecimalNumber|OctalNumber|HexaDecimalNumber;
fragment DecimalNumber: '0'|[1-9][0-9]*;
fragment OctalNumber: '0'[1-7][0-7]*;
fragment HexaDecimalNumber: '0x'[0-9a-fA-F]+;

Identifier: [a-zA-Z][a-zA-Z0-9_]*;
QuotedString: '"' (~["\\\r\n]|'\\'[btnfr"\\]|'\\u'[0-9a-fA-F][0-9a-fA-F][0-9a-fA-F][0-9a-fA-F])* '"';
RegexString: 'R' QuotedString; 

SingleLineComment: '//' ~[\r\n]* -> skip;
MultiLineComment: '/*' .*? '*/' -> skip;
Whitespace: [ \t\r\n]+ -> skip;

// Rules

formatSpecs
	: (symbols|flagSymbols|formatSpec|structSpec|arraySpec|sequenceSpec|unionSpec)*
	;

// Symbol rules

symbols
	: (byteSymbols|wordSymbols|dwordSymbols|qwordSymbols)
	;

byteSymbols
	: symbolsIdentifier Colon ByteSymbols LCBracket symbolDefinition+ RCBracket
	;

wordSymbols
	: symbolsIdentifier Colon WordSymbols LCBracket symbolDefinition+ RCBracket
	;

dwordSymbols
	: symbolsIdentifier Colon DWordSymbols LCBracket symbolDefinition+ RCBracket
	;

qwordSymbols
	: symbolsIdentifier Colon QWordSymbols LCBracket symbolDefinition+ RCBracket
	;

flagSymbols
	: (byteFlagSymbols|wordFlagSymbols|dwordFlagSymbols|qwordFlagSymbols)
	;

byteFlagSymbols
	: symbolsIdentifier Colon ByteFlagSymbols LCBracket symbolDefinition+ RCBracket
	;

wordFlagSymbols
	: symbolsIdentifier Colon WordFlagSymbols LCBracket symbolDefinition+ RCBracket
	;

dwordFlagSymbols
	: symbolsIdentifier Colon DWordFlagSymbols LCBracket symbolDefinition+ RCBracket
	;

qwordFlagSymbols
	: symbolsIdentifier Colon QWordFlagSymbols LCBracket symbolDefinition+ RCBracket
	;
	
symbolDefinition
	: symbolValue Colon symbol
	;

symbolsIdentifier
	: Identifier
	;
	
symbolValue
	: Number
	;
	
symbol
	: QuotedString
	;

// Spec rules

formatSpec
	: specIdentifier Colon FormatSpec textExpression LCBracket structSpecElement+ RCBracket(Apply (compositeSpecByteOrderModifier|compositeSpecExportModifier|compositeSpecRendererModifier))*
	;
	
mimeTypeIdentifier
	: Identifier
	;

structSpec
	: specIdentifier Colon anonymousStructSpec
	;
	
anonymousStructSpec
	: Struct textExpression? LCBracket structSpecElement+ RCBracket (Apply (compositeSpecByteOrderModifier|compositeSpecExportModifier|compositeSpecRendererModifier))*
	;
	
structSpecElement
	: (specReference|attributeSpec|anonymousStructSpec|anonymousArraySpec|anonymousSequenceSpec|anonymousUnionSpec|conditionalSpec|encodedInputSpec|decodeAtSpec)
	;
	
arraySpec
	: specIdentifier Colon anonymousArraySpec
	;
	
anonymousArraySpec
	: Array LSBracket numberExpression RSBracket textExpression? LCBracket attributeSpec+ RCBracket (Apply (compositeSpecByteOrderModifier|compositeSpecExportModifier|compositeSpecRendererModifier))*
	;
	
sequenceSpec
	: specIdentifier Colon anonymousSequenceSpec
	;
	
anonymousSequenceSpec
	: Sequence textExpression? structSpecElement (Apply (sequenceSpecStopBeforeModifier|sequenceSpecStopAfterModifier|sequenceSpecMinModifier|sequenceSpecMaxModifier|sequenceSpecSizeModifier|compositeSpecByteOrderModifier|compositeSpecExportModifier|compositeSpecRendererModifier))*
	;
	
sequenceSpecStopBeforeModifier
	: StopBefore LBracket specReference RBracket
	;
	
sequenceSpecStopAfterModifier
	: StopAfter LBracket specReference RBracket
	;
	
sequenceSpecMinModifier
	: Min LBracket numberExpression RBracket
	;
	
sequenceSpecMaxModifier
	: Max LBracket numberExpression RBracket
	;
	
sequenceSpecSizeModifier
	: Size LBracket numberExpression RBracket
	;

unionSpec
	: specIdentifier Colon anonymousUnionSpec
	;

anonymousUnionSpec
	: Union textExpression? LCBracket compositeSpecExpression+ RCBracket (Apply (compositeSpecByteOrderModifier|compositeSpecExportModifier|compositeSpecRendererModifier))*
	;
	
compositeSpecByteOrderModifier
	: (LittleEndian|BigEndian) LBracket RBracket
	;
	
compositeSpecExportModifier
	: Export LBracket externalReference RBracket
	;
	
compositeSpecRendererModifier
	: Renderer LBracket externalReference RBracket
	;

compositeSpecExpression
	: (specReference|anonymousStructSpec|anonymousArraySpec|anonymousSequenceSpec|anonymousUnionSpec|conditionalCompositeSpec)
	;
	
conditionalSpec
	: Conditional externalReference LCBracket specReference* RCBracket
	;
	
conditionalCompositeSpec
	: Conditional externalReference LCBracket specReference* RCBracket
	;
	
encodedInputSpec
	: Encoded externalReference
	;

decodeAtSpec
	: DecodeAt numberExpression (Colon numberValue)? compositeSpecExpression
	;
	
attributeSpec
	: (byteAttributeSpec|wordAttributeSpec|dwordAttributeSpec|qwordAttributeSpec|byteArrayAttributeSpec|wordArrayAttributeSpec|dwordArrayAttributeSpec|qwordArrayAttributeSpec|charArrayAttributeSpec|stringAttributeSpec|rangeAttributeSpec|scanAttributeSpec)
	;

byteAttributeSpec
	: (specIdentifier (At scopeIdentifier)? Colon)? Byte textExpression (Apply (attributeValidateNumberModifier|attributeFormatModifier|attributeRendererModifier|attributeLinkModifier))*
	;

wordAttributeSpec
	: (specIdentifier (At scopeIdentifier)? Colon)? Word textExpression (Apply (attributeValidateNumberModifier|attributeFormatModifier|attributeRendererModifier|attributeLinkModifier))*
	;

dwordAttributeSpec
	: (specIdentifier (At scopeIdentifier)? Colon)? DWord textExpression (Apply (attributeValidateNumberModifier|attributeFormatModifier|attributeRendererModifier|attributeLinkModifier))*
	;

qwordAttributeSpec
	: (specIdentifier (At scopeIdentifier)? Colon)? QWord textExpression (Apply (attributeValidateNumberModifier|attributeFormatModifier|attributeRendererModifier|attributeLinkModifier))*
	;

byteArrayAttributeSpec
	: (specIdentifier (At scopeIdentifier)? Colon)? Byte LSBracket numberExpression RSBracket textExpression (Apply (attributeValidateNumberArrayModifier|attributeFormatModifier|attributeRendererModifier))*
	;

wordArrayAttributeSpec
	: (specIdentifier (At scopeIdentifier)? Colon)? Word LSBracket numberExpression RSBracket textExpression (Apply (attributeValidateNumberArrayModifier|attributeFormatModifier|attributeRendererModifier))*
	;

dwordArrayAttributeSpec
	: (specIdentifier (At scopeIdentifier)? Colon)? DWord LSBracket numberExpression RSBracket textExpression (Apply (attributeValidateNumberArrayModifier|attributeFormatModifier|attributeRendererModifier))*
	;

qwordArrayAttributeSpec
	: (specIdentifier (At scopeIdentifier)? Colon)? QWord LSBracket numberExpression RSBracket textExpression (Apply (attributeValidateNumberArrayModifier|attributeFormatModifier|attributeRendererModifier))*
	;

charArrayAttributeSpec
	: (specIdentifier (At scopeIdentifier)? Colon)? Char LSBracket numberExpression RSBracket textExpression (Apply (attributeValidateStringModifier|stringAttributeCharsetModifier|attributeFormatModifier|attributeRendererModifier))*
	;
	
stringAttributeSpec
	: (specIdentifier (At scopeIdentifier)? Colon)? String textExpression (Apply (attributeValidateStringModifier|stringAttributeCharsetModifier|attributeFormatModifier|attributeRendererModifier))*
	;

rangeAttributeSpec
	: (specIdentifier (At scopeIdentifier)? Colon)? Range LSBracket (numberExpression|Ellipsis) RSBracket textExpression (Apply (attributeRendererModifier))*
	;
	
scanAttributeSpec
	: (specIdentifier (At scopeIdentifier)? Colon)? Scan LSBracket (numberExpression|Ellipsis)? (Comma numberExpression)?  RSBracket textExpression externalReference (Apply (attributeRendererModifier))*
	;
	
attributeValidateNumberModifier
	: Validate LBracket (numberValueSet|specReference) RBracket
	;
	
attributeValidateNumberArrayModifier
	: Validate LBracket numberArrayValueSet RBracket
	;

attributeValidateStringModifier
	: Validate LBracket validationTextSet RBracket
	;

attributeFormatModifier
	: Format LBracket (formatText|specReference) RBracket
	;
	
attributeRendererModifier
	: Renderer LBracket specReference RBracket
	;

attributeLinkModifier
	: Link LBracket numberExpression? RBracket
	;
	
stringAttributeCharsetModifier
	: Charset LBracket simpleText RBracket
	;
	
specIdentifier
	: Identifier
	;
	
scopeIdentifier
	: specIdentifier
	;

numberExpression
	: (numberValue|specReference|externalReference)
	;

numberValue
	: Number
	;

numberValueSet
	: numberValue (Or numberValue)*
	;

numberArrayValue
	: LCBracket (numberValue (Comma numberValue)* )? RCBracket
	;
	
numberArrayValueSet
	: numberArrayValue (Or numberArrayValue)*
	;
	
textExpression
	: (simpleText|Text LBracket formatText (Comma (formatTextArgument))* RBracket)
	;
	
formatTextArgument
	: (specReference|externalReference)
	;
	
simpleText
	: QuotedString
	;
	
regexText
	: RegexString
	;

validationText
	: (simpleText|regexText)
	;

validationTextSet
	: validationText (Or validationText)*
	;
	
formatText
	: QuotedString
	;

specReference
	: At referencedSpec
	;
	
referencedSpec
	: specIdentifier
	;

externalReference
	: Hash referencedExternal
	;
	
referencedExternal
	: Identifier
	;
