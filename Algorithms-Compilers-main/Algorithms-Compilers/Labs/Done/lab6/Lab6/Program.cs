using System;
using System.Collections.Generic;
using System.Text.RegularExpressions;

namespace eMarkProcessor
{
    class Program
    {
        static void Main(string[] args)
        {
            Console.WriteLine("Введите путь к файлу с документом eMark:");
            string filePath = Console.ReadLine();

            if (System.IO.File.Exists(filePath))
            {
                string[] lines = System.IO.File.ReadAllLines(filePath);

                try
                {
                    var processor = new EMarkProcessor();
                    processor.ProcessDocument(lines);
                }
                catch (Exception ex)
                {
                    Console.WriteLine($"Ошибка: {ex.Message}");
                }
            }
            else
            {
                Console.WriteLine("Файл не найден.");
            }
        }
    }

    public class EMarkProcessor
    {
        private static readonly Regex TagRegex = new Regex(@"<(?<tag>\w+)(?<attributes>[^>]*)>(?<content>.*?)</\k<tag>>",
                                                           RegexOptions.Singleline | RegexOptions.Compiled);
        private const int ConsoleWidth = 80;

        public void ProcessDocument(string[] lines)
        {
            string document = string.Join(Environment.NewLine, lines);
            ParseTag(document, "block");
        }

        private void ParseTag(string document, string expectedTag)
        {
            var matches = TagRegex.Matches(document);

            foreach (Match match in matches)
            {
                string tag = match.Groups["tag"].Value;
                string attributes = match.Groups["attributes"].Value;
                string content = match.Groups["content"].Value.Trim();

                if (tag != expectedTag)
                {
                    throw new Exception($"Ожидался тэг <{expectedTag}>, но найден <{tag}>.");
                }

                if (tag == "block")
                {
                    ValidateBlock(attributes, content);
                }
                else if (tag == "row")
                {
                    ProcessRow(attributes, content);
                }
            }
        }

        private void ValidateBlock(string attributes, string content)
        {
            int rows = ExtractAttributeValue(attributes, "rows");
            int columns = ExtractAttributeValue(attributes, "columns");

            if (rows <= 0 || columns <= 0)
            {
                throw new Exception("Атрибуты rows и columns должны быть положительными.");
            }

            ParseTag(content, "row");
        }

        private void ProcessRow(string attributes, string content)
        {
            var matches = TagRegex.Matches(content);

            foreach (Match match in matches)
            {
                string tag = match.Groups["tag"].Value;
                string columnAttributes = match.Groups["attributes"].Value;
                string columnContent = match.Groups["content"].Value.Trim();

                if (tag == "column")
                {
                    RenderColumn(columnAttributes, columnContent);
                }
            }

            Console.WriteLine(); // Переход на следующую строку
        }

        private void RenderColumn(string attributes, string content)
        {
            int width = ExtractAttributeValue(attributes, "width");
            int textColor = ExtractAttributeValue(attributes, "textcolor", 15); // Белый по умолчанию
            int bgColor = ExtractAttributeValue(attributes, "bgcolor", 0); // Черный по умолчанию
            string halign = ExtractAttributeValue(attributes, "halign", "left");

            string formattedContent = ApplyAlignment(content, width, halign);
            ApplyConsoleStyles(textColor, bgColor);
            Console.Write(formattedContent);
            ResetConsoleStyles();
        }

        private string ApplyAlignment(string content, int width, string alignment)
        {
            if (content.Length > width)
            {content = content.Substring(0, width);
            }

            return alignment switch
            {
                "center" => content.PadLeft((width + content.Length) / 2).PadRight(width),
                "right" => content.PadLeft(width),
                _ => content.PadRight(width), // По умолчанию выравнивание по левому краю
            };
        }

        private void ApplyConsoleStyles(int textColor, int bgColor)
        {
            Console.ForegroundColor = (ConsoleColor)textColor;
            Console.BackgroundColor = (ConsoleColor)bgColor;
        }

        private void ResetConsoleStyles()
        {
            Console.ResetColor();
        }

        private int ExtractAttributeValue(string attributes, string attributeName, int defaultValue = -1)
        {
            var match = Regex.Match(attributes, $@"{attributeName}=""(\d+)""");
            if (match.Success)
            {
                return int.Parse(match.Groups[1].Value);
            }

            if (defaultValue >= 0)
            {
                return defaultValue;
            }

            throw new Exception($"Атрибут {attributeName} не найден.");
        }

        private string ExtractAttributeValue(string attributes, string attributeName, string defaultValue)
        {
            var match = Regex.Match(attributes, $@"{attributeName}=""(.*?)""");
            return match.Success ? match.Groups[1].Value : defaultValue;
        }
    }
}