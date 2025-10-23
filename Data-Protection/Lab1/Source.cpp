#include <iostream>
#include <fstream>
#include <string>
#include <filesystem>
#include <map>
#include <vector>
#include <algorithm>

#define FILE_NAME "src.doc"

enum class Type {
	Encrypt,
	Decrypt
};

void firstTask()
{
	//std::cout << "Size: " << std::filesystem::file_size("src.doc");
}

void secondTask()
{
	std::ifstream file("src.doc", std::ios::binary);
	std::string str;
	std::getline(file, str);

	std::map<unsigned char, float> freq;

	for (int i = 0; i < str.size(); i++)
		freq[str[i]] += 1;

	for (auto& var : freq) {
		var.second /= sizeof(str);
		std::cout << "'" << var.first << "' - " << var.second << std::endl;
	}
}

int findPos(std::string& str, char ch)
{
	for (int i = 0; i < str.size(); i++)
		if (str[i] == ch)
			return i;
}

void thirdTask(Type type)
{
	std::ifstream encryptFile(FILE_NAME, std::ios::binary);
	std::ifstream keyFile("key.txt", std::ios::binary);

	std::string str;
	std::string buf;

	std::vector<int> key;

	while (std::getline(keyFile, buf)) { key.push_back(stoi(buf)); }

	char ch = 0;
	switch (type) {
	case Type::Decrypt:
		while (encryptFile.get(ch)) {
			unsigned char val = unsigned char(ch);
			unsigned char buf_ch = char(std::distance(key.begin(), std::find(key.begin(), key.end(), int(val))));
			str += buf_ch;
		}
		break;
	case Type::Encrypt:
		while (encryptFile.get(ch)) {
			unsigned char val = unsigned char(ch);
			unsigned char buf_ch = unsigned char(key[int(val)]);
			str += buf_ch;
		}
		break;
	}

	std::ofstream file(FILE_NAME, std::ios::binary);
	file << str;

	//std::cout << str << std::endl;

}



int main()
{
	//firstTask();
	//secondTask();

	thirdTask(Type::Decrypt);

	





	//std::ofstream file("key.txt", std::ios::app|std::ios::binary);

	//for (int i = 256; i >= 0; i--)
		//file << i << std::endl;

	//std::ifstream encryptFile(FILE_NAME, std::ios::binary);

	//char ch = 0;
	//while (encryptFile.get(ch)) {
	//	std::cout << static_cast<int>(static_cast<unsigned char>(ch)) << std::endl;
	//}

	return 0;
}
