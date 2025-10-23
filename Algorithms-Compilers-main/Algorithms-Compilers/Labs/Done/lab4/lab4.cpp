#include <iostream>
#include <fstream>
#include <stack>
#include <string>
#include <cctype>
#include <map>
#include <set>
#include <vector>
#include <iterator>
#include <algorithm>


// Таблица предиктивного анализа
std::map<std::string, std::map<std::string, std::string>> parseTable;

// Синхронизирующие токены
std::set<std::string> syncTokens = { ")", ";", "}" };

// Грамматика
std::map<std::string, std::vector<std::vector<std::string>>> grammar;

std::map<std::string, std::set<std::string>> FIRST_cache;
std::map<std::string, std::set<std::string>> FOLLOW_cache;

// Функция для вычисления FIRST для нетерминала
std::set<std::string> FIRST(std::string nonTerm) {
    if (FIRST_cache.find(nonTerm) != FIRST_cache.end()) {
        return FIRST_cache[nonTerm]; // Если значение уже вычислено, возвращаем из кэша
    }

    std::set<std::string> firstSet;

    // Проходим по всем правилам грамматики для данного нетерминала
    for (const auto& production : grammar[nonTerm]) {
        if (production.empty()) {
            continue;
        }

        auto item = production[0];
        // Если первый символ — терминал, добавляем его в FIRST
        if (grammar.find(item) == grammar.end()) {
            firstSet.insert(item);
        }
        else {  // Если это нетерминал, рекурсивно находим его FIRST
            std::set<std::string> temp = FIRST(item);
            firstSet.insert(temp.begin(), temp.end());
        }
    }

    FIRST_cache[nonTerm] = firstSet;  // Сохраняем результат в кэш
    return firstSet;
}

// Функция для вычисления FOLLOW для нетерминала
std::set<std::string> FOLLOW(std::string nonTerm) {
    if (FOLLOW_cache.find(nonTerm) != FOLLOW_cache.end()) {
        return FOLLOW_cache[nonTerm]; // Если значение уже вычислено, возвращаем из кэша
    }

    std::set<std::string> followSet;

    if (nonTerm == "<program>") {
        followSet.insert("$"); // Стартовый нетерминал имеет $ в FOLLOW
    }

    // Проходим по грамматике и находим все места, где встречается этот нетерминал
    for (const auto& rule : grammar) {
        for (const auto& production : rule.second) {
            auto it = std::find(production.begin(), production.end(), nonTerm);
            if (it != production.end()) {
                auto nextSymbol = std::next(it);
                if (nextSymbol != production.end()) {
                    // Если nextSymbol терминал, добавляем его в set
                    if (grammar.find(*nextSymbol) == grammar.end()) {
                        followSet.insert(*nextSymbol);
                    }
                    else {  // Если nextSymbol нетерминал, берем его FIRST в FOLLOW
                        std::set<std::string> temp = FIRST(*nextSymbol);
                        followSet.insert(temp.begin(), temp.end());
                    }
                }
                // Если следующего символа нет
                else {
                    if (rule.first != nonTerm) {
                        std::set<std::string> parentFollow = FOLLOW(rule.first);
                        followSet.insert(parentFollow.begin(), parentFollow.end());
                    }
                }
            }
        }
    }

    FOLLOW_cache[nonTerm] = followSet;
    return followSet;
}

// Проверка терминала
bool isTerminal(const std::string& symbol) {
    return !symbol.empty() && (islower(symbol[0]) || ispunct(symbol[0]));
}

// Вывод ошибок
void reportError(const std::string& message, int line) {
    std::cout << "Error: " << message << " on line " << line << std::endl;
}

// Нерекурсивный предиктивный анализ
void predictiveParse(std::vector<std::string>& tokens) {
    std::stack<std::string> parseStack;
    parseStack.push("$");
    parseStack.push("<program>");

    size_t currentTokenIndex = 0;

    while (!parseStack.empty()) {
        std::string top = parseStack.top();
        parseStack.pop();

        if (isTerminal(top)) {
            if (top == tokens[currentTokenIndex]) {
                ++currentTokenIndex; // Совпадение
            }
            else {
                reportError("Incorrect symbol '" + tokens[currentTokenIndex] + "'", currentTokenIndex + 1);
                // Восстановление в режиме паники
                while (currentTokenIndex < tokens.size() && syncTokens.find(tokens[currentTokenIndex]) == syncTokens.end()) {
                    ++currentTokenIndex;
                }
            }
        }
        else if (parseTable[top].count(tokens[currentTokenIndex])) {
            std::string production = parseTable[top][tokens[currentTokenIndex]];
            if (production != "$") { // Не пустая продукция
                for (auto it = production.rbegin(); it != production.rend(); ++it) {
                    parseStack.push(std::string(1, *it));
                }
            }
        }
        else {
            reportError("Incorrect strcut " + top, currentTokenIndex + 1);
            // Восстановление в режиме паники
            while (currentTokenIndex < tokens.size() && syncTokens.find(tokens[currentTokenIndex]) == syncTokens.end()) {
                ++currentTokenIndex;
            }
        }
    }
}

void setGrammer()
{
    grammar["<program>"] = { {"<type>", "main", "(", ")", "{", "<statement>", "}"} };
    grammar["<type>"] = { {"int"}, {"bool"}, {"void"} };
    grammar["<statement>"] = {
            {"<declaration>", ";"}, {"{", "<statement>", "}"},
            {"<for>", "<statement>"}, {"<if>", "<statement>"}, {"<return>"}
    };
    grammar["<declaration>"] = { {"<type>", "<identifier>", "<assign>"} };
    grammar["<identifier>"] = { {"<character>", "<id_end>"} };
    grammar["<character>"] = { { "a" }, { "b" }, { "c" }, { "d" }, { "e" }, { "f" }, { "g" }, { "h" }, { "i" }, { "j" }, { "k" }, { "l" }, { "m" }, { "n" }, { "o" }, { "p" }, { "q" }, { "r" }, { "s" }, { "t" }, { "u" }, { "v" }, { "w" }, { "x" }, { "y" }, { "z" }, { "A" }, { "B" }, { "C" }, { "D" }, { "E" }, { "F" }, { "G" }, { "H" }, { "I" }, { "J" }, { "K" }, { "L" }, { "M" }, { "N" }, { "O" }, { "P" }, { "Q" }, { "R" }, { "S" }, { "T" }, { "U" }, { "V" }, { "W" }, { "X" }, { "Y" }, { "Z" }, { "_" } };
    grammar["<id_end>"] = { {"<character>", "<id_end>"} };
    grammar["<assign>"] = { {"=", "<assign_end>"} };
    grammar["<assign_end>"] = { {"<identifier>"}, {"<number>" }};
    grammar["<number>"] = {{"<digit>", "<number_end>"}};
    grammar["<digit>"] = {{"0"}, {"1"}, {"2"}, {"3"}, {"4"}, {"5"}, {"6"}, {"7"}, {"8"}, {"9"}};
    grammar["<number_end>"] = {{"<digit>", "<number_end>" }};
    grammar["<for>"] = {{"for", "(", "<declaration>", ";", "<bool_expression>", ";", ")"}};
    grammar["<bool_expression>"] = { {"<identifier>", "<relop>", "<identifier>"}, {"<number>", "<relop>", "<identifier>" }};
    grammar["<relop>"] = { {"<"}, {">"}, {"=="}, {"!="} };
    grammar["<if>"] = {{"if", "(", "<bool_expression>", ")"}};
    grammar["<return>"] = { { "return", "<number>", ";" } };
}

std::set<std::string> getTerm()
{
    std::set<std::string> terms;
    for (const auto& rule : grammar)
    {
        for (const auto& prod : rule.second)
        {
            for (const auto& item : prod)
            {
                if (item[0] != '<' && item[item.size() - 1] != '>')
                    terms.insert(item);
            }
        }
    }

    return terms;
}

void setTable()
{
    for (const auto& rule : grammar) {
        const std::string& nonTerminal = rule.first;
        for (const auto& productions : rule.second) 
        {
            for (const auto& prod : productions)
            {
                std::set<std::string> lstFirst = FIRST(nonTerminal);
                for (const auto& terminal : lstFirst) {
                    parseTable[nonTerminal][terminal] = prod;
                }

                if (std::find(lstFirst.begin(), lstFirst.end(), "") != lstFirst.end()) {
                    auto lstFollow = FOLLOW(nonTerminal);
                    for (const auto& followSymbol : lstFollow) {
                        parseTable[nonTerminal][followSymbol] = "";
                    }
                }
            }
        }
    }
}

int main() {
    setGrammer();
    setTable();

    // Чтение входного файла
    std::ifstream inputFile("input.txt");
    if (!inputFile.is_open()) {
        std::cerr << "Не удалось открыть файл." << std::endl;
        return 1;
    }

    std::vector<std::string> tokens;
    std::string token;
    while (inputFile >> token) {
        tokens.push_back(token);
    }
    tokens.push_back("$"); // Конец потока

    // Выполнение синтаксического анализа
    predictiveParse(tokens);

    return 0;
}
