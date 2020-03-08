grep -e '\(^[0-9]\{3\}-[0-9]\{3\}-[0-9]\{4\}$\)' -e '\(^([0-9]\{3\})[ ]\{1\}[0-9]\{3\}-[0-9]\{4\}$\)' file.txt

#Brief synax of regex in bash:
#1. Use `\` to escape next one trailing character, which means the character is going to be interpreted literally.
#2. `^` is used to denote the beginning of a line.
#3. `$` is used to denote the end of a line.
#4. `{n}` is used to denote to match exactly n times of the previous occurance/regex. Don't forget to use `\` to escape `{` and `}`.
#5. `(...)` is used to group regex/pattern together. Don't forget to use `\` to excape `(` and `)`.
#6. `*` is used to match any times of the preceding occurance/regex, including 0 times.
#7. `[]` is used to includes a set of character to be matched for single regex. e.g. `[xyz]` matches one of x, y or z.
#8. Sometimes `^` negates the meaning of the following regex, e.g. `[^a-z]` matches anything character except a to z.