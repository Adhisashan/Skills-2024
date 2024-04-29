from transformers import GPT2LMHeadModel, GPT2Tokenizer
from transformers import T5Tokenizer, T5ForConditionalGeneration
from transformers import AutoModelForCausalLM, AutoTokenizer,AutoModelForSeq2SeqLM

model = GPT2LMHeadModel.from_pretrained("gpt2")
tokenizer = GPT2Tokenizer.from_pretrained("gpt2")

# model = AutoModelForCausalLM.from_pretrained("microsoft/phi-2", torch_dtype="auto", trust_remote_code=True)
# tokenizer = AutoTokenizer.from_pretrained("microsoft/phi-2", trust_remote_code=True)


# tokenizer = T5Tokenizer.from_pretrained("google/flan-t5-large")
# model = T5ForConditionalGeneration.from_pretrained("google/flan-t5-large")




force_word = "gaap"

force_words_ids = [
    tokenizer([force_word], add_prefix_space=True, add_special_tokens=False).input_ids
]

starting_text = [" "]

input_ids = tokenizer(starting_text, return_tensors="pt").input_ids

outputs = model.generate(
    input_ids,
    force_words_ids=force_words_ids,
    num_beams=10,
    max_new_tokens=100,
    remove_invalid_values=True,
)

print("Output:\n" + 100 * '-')
print(tokenizer.decode(outputs[0], skip_special_tokens=True))