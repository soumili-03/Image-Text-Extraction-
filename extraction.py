import io
import pytesseract
from fastapi import FastAPI, UploadFile, File
from PIL import Image
import torch
import numpy as np

app = FastAPI()

# Load BERT model and tokenizer for question answering
# You might need to use a different model for better text extraction accuracy
# model = BertForQuestionAnswering.from_pretrained("your-pretrained-model")
# tokenizer = BertTokenizer.from_pretrained("your-pretrained-model")


@app.post("/extract_text")
async def extract_text(image: UploadFile = File(...)):
    # Read the uploaded image file
    contents = await image.read()
    
    # Load the image directly in grayscale mode using PIL
    with Image.open(io.BytesIO(contents)).convert("L") as img:
        # Extract text using Pytesseract OCR
        extracted_text = pytesseract.image_to_string(img)
    
    return {"extracted_text": extracted_text}




