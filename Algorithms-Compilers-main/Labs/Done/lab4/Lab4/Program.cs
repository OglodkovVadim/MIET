using System;
using System.Collections.Generic;
using System.Linq;

class Program
{
    // Таблица предиктивного анализа
    static Dictionary<string, Dictionary<string, string>> parseTable = new Dictionary<string, Dictionary<string, string>>();

    // Грамматика
    static Dictionary<string, List<List<string>>> grammar = new Dictionary<string, List<List<string>>>();

    // Кэши для FIRST и FOLLOW
    static Dictionary<string, HashSet<string>> FIRST_cache = new Dictionary<string, HashSet<string>>();
    static Dictionary<string, HashSet<string>> FOLLOW_cache = new Dictionary<string, HashSet<string>>();

    // Синхронизирующие токены
    static HashSet<string> syncTokens = new HashSet<string> { ")", ";", "}" };

    static void Main()
    {
        SetGrammar();
        SetTable();

        Console.WriteLine("Таблица предиктивного анализа:");
        printTable();

        // Пример входного файла
        string[] tokens;
        try
        {
            tokens = File.ReadAllText("input.txt").Replace("main ()", "main ( )").Split(new[] { ' ', '\n', '\r' }, StringSplitOptions.RemoveEmptyEntries);
        }
        catch (Exception ex)
        {
            Console.WriteLine($"Ошибка чтения файла: {ex.Message}");
            return;
        }
        var tokenList = new List<string>(tokens) { "$" }; // Добавляем символ конца потока

        Console.WriteLine("Токены:");
        foreach (var token in tokenList)
            Console.WriteLine(token);

        if (PredictiveParse(tokenList))
            Console.WriteLine("Analyze successful");
        else
            Console.WriteLine("Analyze failed");
    }

    static void SetGrammar()
    {
        grammar["<program>"] = new List<List<string>> { new List<string> { "<type>", "main", "(", ")", "{", "<statement>", "}" } };
        grammar["<type>"] = new List<List<string>> { new List<string> { "int" }, new List<string> { "bool" }, new List<string> { "void" } };
        grammar["<statement>"] = new List<List<string>>
        {
            new List<string> { "<declaration>", ";" },
            new List<string> { "{", "<statement>", "}" },
            new List<string> { "<for>", "<statement>" },
            new List<string> { "<if>", "<statement>" },
            new List<string> { "<return>" }
        };
        grammar["<declaration>"] = new List<List<string>> { new List<string> { "<type>", "<identifier>", "<assign>" } };
        grammar["<identifier>"] = new List<List<string>> { new List<string> { "<character>", "<id_end>" } };
        grammar["<character>"] = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_".Select(c => new List<string> { c.ToString() }).ToList();
        grammar["<id_end>"] = new List<List<string>> { new List<string> { "<character>", "<id_end>" }, new List<string> { "ε" } };
        grammar["<assign>"] = new List<List<string>> { new List<string> { "=", "<assign_end>" }, new List<string> { "ε" } };
        grammar["<assign_end>"] = new List<List<string>> { new List<string> { "<identifier>" }, new List<string> { "<number>" } };
        grammar["<number>"] = new List<List<string>> { new List<string> { "<digit>", "<number_end>" } };
        grammar["<digit>"] = "0123456789".Select(c => new List<string> { c.ToString() }).ToList();
        grammar["<number_end>"] = new List<List<string>> { new List<string> { "<digit>", "<number_end>" }, new List<string> { "ε" } };
        grammar["<for>"] = new List<List<string>> { new List<string> { "for", "(", "<declaration>", ";", "<bool_expression>", ";", ")" } };
        grammar["<bool_expression>"] = new List<List<string>>
        {
            new List<string> { "<identifier>", "<relop>", "<identifier>" },
            new List<string> { "<number>", "<relop>", "<identifier>" }
        };
        grammar["<relop>"] = new List<List<string>> { new List<string> { "<" }, new List<string> { ">" }, new List<string> { "==" }, new List<string> { "!=" } };
        grammar["<if>"] = new List<List<string>> { new List<string> { "if", "(", "<bool_expression>", ")" } };
        grammar["<return>"] = new List<List<string>> { new List<string> { "return", "<number>", ";" } };
    }

    static void SetTable()
    {
        foreach (var rule in grammar)
        {
            var nonTerminal = rule.Key;

            foreach (var production in rule.Value)
            {
                var firstSet = FIRST(production);

                foreach (var terminal in firstSet)
                {
                    if (terminal != "ε")
                    {
                        if (!parseTable.ContainsKey(nonTerminal))
                            parseTable[nonTerminal] = new Dictionary<string, string>();

                        parseTable[nonTerminal][terminal] = string.Join(" ", production);
                    }
                }

                if (firstSet.Contains("ε"))
                {
                    var followSet = FOLLOW(nonTerminal);
                    foreach (var terminal in followSet)
                    {
                        if (!parseTable.ContainsKey(nonTerminal))
                            parseTable[nonTerminal] = new Dictionary<string, string>();

                        parseTable[nonTerminal][terminal] = string.Join(" ", production);
                    }
                }
            }
        }
    }

    static HashSet<string> FIRST(List<string> production)
    {
        var firstSet = new HashSet<string>();

        for (int i = 0; i < production.Count; i++)
        {
            string symbol = production[i];

            if (IsTerminal(symbol))
            {
                firstSet.Add(symbol);
                break;
            }

            if (grammar.ContainsKey(symbol))
            {
                var temp = FIRST(symbol);
                firstSet.UnionWith(temp);

                if (!temp.Contains("ε"))
                    break;
            }

            if (i == production.Count - 1)
                firstSet.Add("ε");
        }

        return firstSet;
    }

    static HashSet<string> FIRST(string nonTerm)
    {
        if (FIRST_cache.ContainsKey(nonTerm))
            return FIRST_cache[nonTerm];

        var firstSet = new HashSet<string>();
        foreach (var production in grammar[nonTerm])
        {
            var temp = FIRST(production);
            firstSet.UnionWith(temp);
        }

        FIRST_cache[nonTerm] = firstSet;
        return firstSet;
    }
    
    static HashSet<string> FOLLOW(string nonTerm)
    {
        if (FOLLOW_cache.ContainsKey(nonTerm))
            return FOLLOW_cache[nonTerm];

        var followSet = new HashSet<string>();

        if (nonTerm == "<program>")
            followSet.Add("$");

        foreach (var rule in grammar)
        {
            foreach (var production in rule.Value)
            {
                int index = production.IndexOf(nonTerm);
                if (index == -1)
                    continue;

                if (index + 1 < production.Count)
                {
                    var nextSymbol = production[index + 1];
                    if (IsTerminal(nextSymbol))
                        followSet.Add(nextSymbol);
                    else
                        followSet.UnionWith(FIRST(nextSymbol));
                }
                else
                {
                    if (rule.Key != nonTerm)
                        followSet.UnionWith(FOLLOW(rule.Key));
                }
            }
        }

        FOLLOW_cache[nonTerm] = followSet;
        return followSet;
    }

    static bool IsTerminal(string symbol) => !string.IsNullOrEmpty(symbol) && !grammar.ContainsKey(symbol) && symbol != "ε";

    static bool PredictiveParse(List<string> tokens)
    {
        var parseStack = new Stack<string>();
        parseStack.Push("$");
        parseStack.Push("<program>");

        int tokenIndex = 0;

        while (parseStack.Count > 0)
        {
            string top = parseStack.Peek();
            string currentToken = tokenIndex < tokens.Count ? tokens[tokenIndex] : "$";

            if (top == currentToken)
            {
                parseStack.Pop();
                tokenIndex++;
            }
            else if (IsTerminal(top))
            {
                Console.WriteLine($"Ошибка: ожидалось {top}, но найдено {currentToken}");
                return false;
            }
            else if (parseTable.ContainsKey(top) && parseTable[top].ContainsKey(currentToken))
            {
                parseStack.Pop();
                var production = parseTable[top][currentToken];
                if (production != "ε")
                {
                    foreach (var symbol in production.Split(' ').Reverse())
                        parseStack.Push(symbol);
                }
            }
            else
            {
                Console.WriteLine($"Ошибка: нет правила для {top} при токене {currentToken}");
                return false;
            }
        }

        return tokenIndex == tokens.Count;
    }

    static void printTable()
    {
        foreach (var nonTerm in parseTable)
        {
            foreach (var term in parseTable[nonTerm.Key])
            {
                Console.WriteLine($"parseTable[{nonTerm.Key}][{term.Key}] = {term.Value}");
            }
        }
    }
}