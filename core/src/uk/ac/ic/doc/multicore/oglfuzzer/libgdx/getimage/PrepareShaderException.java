package uk.ac.ic.doc.multicore.oglfuzzer.libgdx.getimage;

public class PrepareShaderException extends RuntimeException {

  public final boolean compileError;
  public final boolean linkError;

  public PrepareShaderException(String message) {
    this(message, false, false);
  }
  public PrepareShaderException(String message, boolean compileError, boolean linkError) {
    super(message);
    this.compileError = compileError;
    this.linkError = linkError;
  }
}
