package uk.ac.ic.doc.multicore.oglfuzzer.libgdx.getimage;

public class GlErrorException extends RuntimeException {
  public final int glError;

  public GlErrorException(int glError) {
    this.glError = glError;
  }

  @Override
  public String toString() {
    return "glError: " + glError + "; " + super.toString();
  }
}
