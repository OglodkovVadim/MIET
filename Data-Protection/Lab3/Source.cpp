#include <windows.h>
#include <cstdint>
#include <string>
#include <fstream>
#include <iostream>

#define FILE "original.bmp"

void hide_byte_into_pixel(RGBQUAD* pixel, uint8_t hide_byte)
{
	pixel->rgbBlue &= (0xFC);
	pixel->rgbBlue |= (hide_byte >> 6) & 0x3;
	pixel->rgbGreen &= (0xFC);
	pixel->rgbGreen |= (hide_byte >> 4) & 0x3;
	pixel->rgbRed &= (0xFC);
	pixel->rgbRed |= (hide_byte >> 2) & 0x3;
	pixel->rgbReserved &= (0xFC);
	pixel->rgbReserved |= (hide_byte) & 0x3;
}

uint8_t get_byte_from_pixel(RGBQUAD* pixel)
{
	uint8_t byte = 0b00000000;

	byte |= pixel->rgbReserved & 0b00000011;
	byte |= (pixel->rgbRed & 0b00000011) << 2;
	byte |= (pixel->rgbGreen & 0b00000011) << 4;
	byte |= (pixel->rgbBlue & 0b00000011) << 6;

	return byte;
}

void encrypt(const std::string& message)
{
	uint8_t* message_bytes = new uint8_t[message.length() + 1];
	RGBQUAD* image_pixels = new RGBQUAD[message.length()];

	BITMAPFILEHEADER bmp_file_header;
	BITMAPINFOHEADER bmp_info_header;

	for (int i = 0; i < message.length(); i++)
		message_bytes[i] = static_cast<uint8_t>(message[i]);

	message_bytes[message.length()] = 0xFF;

	std::ifstream file_get(FILE, std::ios::binary);

	file_get.read(reinterpret_cast<char*>(&bmp_file_header), sizeof(BITMAPFILEHEADER));
	file_get.read(reinterpret_cast<char*>(&bmp_info_header), sizeof(BITMAPINFOHEADER));

	auto cursor_pos = file_get.tellg();

	for (int i = 0; i < message.length(); i++) {
		file_get.read(reinterpret_cast<char*>(&image_pixels[i].rgbBlue), sizeof(uint8_t));
		file_get.read(reinterpret_cast<char*>(&image_pixels[i].rgbGreen), sizeof(uint8_t));
		file_get.read(reinterpret_cast<char*>(&image_pixels[i].rgbRed), sizeof(uint8_t));
		file_get.read(reinterpret_cast<char*>(&image_pixels[i].rgbReserved), sizeof(uint8_t));
	}

	file_get.close();

	std::ofstream file_put(FILE, std::ios::binary | std::ios::in | std::ios::ate);
	file_put.seekp(cursor_pos);

	for (int i = 0; i <= message.length(); i++) {
		hide_byte_into_pixel(&image_pixels[i], message_bytes[i]);

		file_put.put(image_pixels[i].rgbBlue);
		file_put.put(image_pixels[i].rgbGreen);
		file_put.put(image_pixels[i].rgbRed);
		file_put.put(image_pixels[i].rgbReserved);
	}
	 
	file_put.close();
}

int decrypt()
{
	std::ifstream file(FILE, std::ios::binary);
	std::ofstream file_mess("text.txt");

	BITMAPFILEHEADER bmp_file_header;
	BITMAPINFOHEADER bmp_info_header;

	file.read(reinterpret_cast<char*>(&bmp_file_header), sizeof(BITMAPFILEHEADER));
	file.read(reinterpret_cast<char*>(&bmp_info_header), sizeof(BITMAPINFOHEADER));

	for (int i = 0; i < bmp_info_header.biWidth; i++) {
		for (int j = 0; j < bmp_info_header.biHeight; j++) {
			RGBQUAD pixel;
			file.read(reinterpret_cast<char*>(&pixel.rgbBlue), sizeof(uint8_t));
			file.read(reinterpret_cast<char*>(&pixel.rgbGreen), sizeof(uint8_t));
			file.read(reinterpret_cast<char*>(&pixel.rgbRed), sizeof(uint8_t));
			file.read(reinterpret_cast<char*>(&pixel.rgbReserved), sizeof(uint8_t));

			uint8_t byte = get_byte_from_pixel(&pixel);
			if (byte == 0xFF) {
				file.close();
				file_mess.close();
				return 0;
			}
			file_mess << static_cast<char>(byte);
		}
	}
	file.close();
	file_mess.close();
}

int main()
{
	//encrypt("Hello Alexey");
 	decrypt();

	return 0;
}