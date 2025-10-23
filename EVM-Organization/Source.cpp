#include <iostream>
#include <stdio.h>
#include <bitset>
#include <iomanip>

void viewPointer(void *p)
{
	char* p1 = reinterpret_cast<char*>(p);
	unsigned short* p2 = reinterpret_cast<unsigned short*>(p);
	double* p3 = reinterpret_cast<double*>(p);

	std::cout << "Pointers:" << std::endl;
	std::cout << "p1 = " << p << "\np2 = " << p2 << "\np3 = " << p3 << std::endl;

	std::cout << "Pointers with offset:" << std::endl;
	std::cout << "(p1 + 1) = " << reinterpret_cast<void*>(p1 + 1) << "\n(p2 + 1) = " << (p2 + 1) << "\n(p3 + 1) = " << (p3 + 1) << std::endl;
}

void printPointer(void* p)
{
	//std::cout << *p << std::endl;
	char* p1 = reinterpret_cast<char*>(p);
	unsigned short* p2 = reinterpret_cast<unsigned short*>(p);
	double* p3 = reinterpret_cast<double*>(p);

	std::cout << std::hex << "\n*p1 = " << int((unsigned char)*p1) << "\n*p2 = " << *p2 << "\n*p3 = " << *p3 << std::endl;
}

void printDump(void *p, size_t N)
{
	unsigned char* uc_ptr = reinterpret_cast<unsigned char*>(p);

	for (size_t i = 0; i < N; i++)
		std::cout << std::hex << static_cast<unsigned>(*(uc_ptr + i)) << " ";
	std::cout << std::endl;
}

void print16(void* p)
{
	unsigned short* us_ptr = reinterpret_cast<unsigned short*>(p);
	short* s_ptr = reinterpret_cast<short*>(p);

	std::cout << "Unsigned short: " << std::endl;
	std::cout << std::hex << *us_ptr << std::endl; // hex
	std::cout << std::bitset<16>(*us_ptr) << std::endl; // bin
	std::cout << std::dec << *us_ptr << std::endl; // dec

	std::cout << "\nShort: " << std::endl;
	std::cout << std::hex << *s_ptr << std::endl; // hex
	std::cout << std::bitset<16>(*s_ptr) << std::endl; // bin 
	std::cout << std::dec << *s_ptr << std::endl; // dec
}

void print32(void* p)
{
	unsigned int* us_ptr = reinterpret_cast<unsigned int*>(p);
	int* s_ptr = reinterpret_cast<int*>(p);
	float* f_ptr = reinterpret_cast<float*>(p);


	std::cout << "Unsigned int: " << std::endl;
	std::cout << std::hex << *us_ptr << std::endl; // hex
	std::cout << std::bitset<32>(*us_ptr) << std::endl; // bin
	std::cout << std::dec << *us_ptr << std::endl << std::endl; // dec

	std::cout << "Int: " << std::endl;
	std::cout << std::hex << *s_ptr << std::endl; // hex
	std::cout << std::bitset<32>(*s_ptr) << std::endl; // bin 
	std::cout << std::dec << *s_ptr << std::endl << std::endl; // dec

	std::cout << "Float: " << std::endl;
	std::cout << std::fixed << std::setprecision(2) << *f_ptr << std::endl;
	std::cout << std::scientific << *f_ptr << std::endl << std::endl;
}

void print64(void* p)
{
	unsigned long* ul_ptr = reinterpret_cast<unsigned long*>(p);
	long* l_ptr = reinterpret_cast<long*>(p);
	double* d_ptr = reinterpret_cast<double*>(p);


	std::cout << "Unsigned long: " << std::endl;
	std::cout << std::hex << *ul_ptr << std::endl; // hex
	std::cout << std::bitset<64>(*ul_ptr) << std::endl; // bin
	std::cout << std::dec << *ul_ptr << std::endl << std::endl; // dec

	std::cout << "Long: " << std::endl;
	std::cout << std::hex << *l_ptr << std::endl; // hex
	std::cout << std::bitset<64>(*l_ptr) << std::endl; // bin 
	std::cout << std::dec << *l_ptr << std::endl << std::endl; // dec

	std::cout << "Double: " << std::endl;
	std::cout << std::fixed << std::setprecision(2) << *d_ptr << std::endl;
	std::cout << std::scientific << *d_ptr << std::endl << std::endl;
}

void task1()
{
	int value = 0;
	viewPointer(&value);
}

void task2()
{
	long long first = 0x1122334455667788;
	char second[] = "0123456789abcdef";

	printPointer(&first);
	printPointer(&second);
}

void task3()
{
	int x = 5;
	int y = -5;
	int z = 0xFF007100;

	int* arr = new int[3];
	arr[0] = x;
	arr[1] = y;
	arr[2] = z;

	printDump(&x, sizeof(5));
	printDump(arr, sizeof(int)*3);
	printDump(&y, sizeof(-5));

	delete[] arr;
}
	
void task4()
{
	int value = 65535;
	print16(&value);
}

void task5()
{
	int x = 5;
	int y = -5;
	int z = 0xFF007100;

	print32(&x);
	print32(&y);
	print32(&z);
}

void task6()
{
	int x = 5;
	int y = -5;
	int z = 0xFF007100;

	print64(&x);
	print64(&y);
	print64(&z);
}


int main()
{
	//task1();
	//task2();
	//task3();
	//task4();
	//task5();
	//task6();

	return 0;
}