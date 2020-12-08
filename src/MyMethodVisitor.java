package com.agent;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class MyMethodVisitor extends MethodVisitor {
    public MyMethodVisitor(MethodVisitor methodVisitor) {
        super(Opcodes.ASM9, methodVisitor);
    }

    @Override
    public void visitInsn(int opcode) {
        if (opcode >= Opcodes.IALOAD && opcode <= Opcodes.SALOAD) {
            super.visitInsn(Opcodes.DUP2);
            super.visitMethodInsn(Opcodes.INVOKESTATIC, "com/agent/Mytrace", "traceArrayRead", "(Ljava/lang/Object;I)V", false);
        }
        if (opcode >= Opcodes.IASTORE && opcode <= Opcodes.SASTORE) {
            super.visitInsn(Opcodes.DUP2_X1);
            super.visitInsn(Opcodes.POP);
            super.visitInsn(Opcodes.DUP2);
            super.visitMethodInsn(Opcodes.INVOKESTATIC, "com/agent/Mytrace", "traceArrayWrite", "(Ljava/lang/Object;I)V", false);
            super.visitInsn(Opcodes.POP);
            super.visitInsn(Opcodes.DUP_X2);
            super.visitInsn(Opcodes.POP);
        }
        super.visitInsn(opcode);
    }

    @Override
    public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
        if (opcode == Opcodes.GETSTATIC) {
            super.visitLdcInsn(owner);
            super.visitLdcInsn(name);
            super.visitMethodInsn(Opcodes.INVOKESTATIC, "com/agent/Mytrace", "traceStaticFieldRead", "(Ljava/lang/String;Ljava/lang/String;)V", false);

        }

        if (opcode == Opcodes.PUTSTATIC) {
            super.visitLdcInsn(owner);
            super.visitLdcInsn(name);
            super.visitMethodInsn(Opcodes.INVOKESTATIC, "com/agent/Mytrace", "traceStaticFieldWrite", "(Ljava/lang/String;Ljava/lang/String;)V", false);
        }

        if (opcode == Opcodes.GETFIELD) {
            super.visitInsn(Opcodes.DUP);
            super.visitLdcInsn(name);
            super.visitMethodInsn(Opcodes.INVOKESTATIC, "com/agent/Mytrace", "traceFieldRead", "(Ljava/lang/Object;Ljava/lang/String;)V", false);
        }

        if (opcode == Opcodes.PUTFIELD) {
            super.visitInsn(Opcodes.DUP2);
            super.visitInsn(Opcodes.POP);
            super.visitLdcInsn(name);
            super.visitMethodInsn(Opcodes.INVOKESTATIC, "com/agent/Mytrace", "traceFieldWrite", "(Ljava/lang/Object;Ljava/lang/String;)V", false);
        }
        //TODO 这里存在一个问题，必须调用Mytrace随便执行一个方法，才能正常运行，否则会报栈溢出的错误
        Mytrace.must();
        super.visitFieldInsn(opcode, owner, name, descriptor);
    }


}
