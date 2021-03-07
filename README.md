# Cron Expression Parser

The parser takes a single argument which is a well-formed cron expression as is specified BSD's `crontab(5)`    
(excluding any of the special strings `@yearly`, `@monthly`, `@weekly` etc.). The program then outputs a table  
where each field of the expression is expanded.  

For example the expression `*/15 0 1,15 * 1-5 /usr/bin/find` will produce the following output:  
```
minutes        0 15 30 45
hours          0
day of month   1 15
month          1 2 3 4 5 6 7 8 9 10 11 12
day of week    1 2 3 4 5
command        /usr/bin/find
```
---
## Usage
From project root, run: `./cronparser "{well-formed-cron-expression}"` (please note, the expression must be surrounded in quotes).
### Example
```shell
[user@host cronparser]> ./cronparser "3-59/15 9-17/2 * jan,feb,mar * /usr/bin/find"
minutes        3 18 33 48
hours          9 11 13 15
day of month   1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31
month          1 2 3
day of week    0 1 2 3 4 5 6 7
command        /usr/bin/find
```
### Allowed values
| Field          | Allowed Values                                           | Allowed special characters | 
| -----------    | -----------                                              | -----------                |
| Minutes        | `0-59`                                                   | `*` `,` `-` `/`            |
| Hours          | `0-23`                                                   | `*` `,` `-` `/`            |
| Day of month   | `1-31`                                                   | `*` `,` `-` `/`            |
| Month          | `1-12` (or 3 character names `jan-feb`)                  | `*` `,` `-` `/`            |
| Day of week    | `0-7` (`0` & `7` is Sun, or 3 character names `mon-sun`) | `*` `,` `-` `/`            |

The order of the fields must be preserved and all fields are manadatory.