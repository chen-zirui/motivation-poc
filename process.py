import difflib
import re

def extract_code_blocks(file_path):
    with open(file_path, 'r', encoding='utf-8') as f:
        text = f.read()

    # 匹配每一组：Test File Content -> Response -> ----------
    pattern = re.compile(
        r"### Test File Content:\n(?P<test>.*?)(?=\n=== Response ===)\n=== Response ===\n(?P<resp>.*?)(?=\n-{10,}|\Z)",
        re.DOTALL
    )

    return pattern.findall(text)

def generate_clean_diffs(code_pairs):
    for idx, (test_code, response_code) in enumerate(code_pairs):
        original = test_code.strip().splitlines(keepends=True)
        modified = response_code.strip().splitlines(keepends=True)
        diff = difflib.unified_diff(
            original, modified,
            fromfile='TestFile.java',
            tofile='ModifiedTestFile.java',
            lineterm=''
        )
        print(f"\n===== Diff #{idx + 1} =====")
        print("".join(diff))

if __name__ == "__main__":
    file_path = "log.txt"
    code_pairs = extract_code_blocks(file_path)
    generate_clean_diffs(code_pairs)
