module fulladder32(
    input logic [31:0] a_i,
    input logic [31:0] b_i,
    input logic carry_i,
    output logic carry_o,
    output logic [31:0] sum_o
);

logic [32:0] c;
assign c[0] = carry_i;
assign carry_o = c[32];

genvar i;
generate
        for (i = 0; i < 32; i=i+1) begin : newgen
                fulladder adder (
                    .a_i(a_i[i]),
                    .b_i(b_i[i]),
                    .carry_i(c[i]),
                    .carry_o(c[i+1]),
                    .sum_o(sum_o[i])
                );
        end
endgenerate        

endmodule