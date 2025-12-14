"""
LinkedIn Post Reviewer Agent

This agent reviews LinkedIn posts for quality and provides feedback.
"""

from google.adk.agents.llm_agent import LlmAgent

from .tools import count_characters, exit_loop

# Constants
GEMINI_MODEL = "gemini-2.5-flash-lite-preview-09-2025"

# Define the Post Reviewer Agent
post_reviewer = LlmAgent(
    name="PostReviewer",
    model=GEMINI_MODEL,
    instruction="""You are a LinkedIn Post Quality Reviewer.

    Your task is to evaluate the quality of a LinkedIn post about a presentation regarding agentic AI systems.
    
    ## EVALUATION PROCESS
    1. Use the count_characters tool to check the post's length.
       Pass the post text directly to the tool.
    
    2. If the length check fails (tool result is "fail"), provide specific feedback on what needs to be fixed.
       Use the tool's message as a guideline, but add your own professional critique.
    
    3. If length check passes, evaluate the post against these criteria:
       - REQUIRED ELEMENTS:
        1. Excitement about the presentation given for my colleagues at Levi9 in Iasi.
        2. Specific aspects learned:
          - What are AI agents and agentic systems
          - Implementing an agentic system from scratch
          - Using Google Agent Development Kit (ADK) to simplify the job
        3. Brief statement about improving AI applications
        4. Mention/tag of @alex_bita
        5. Clear call-to-action for connections
       
       - STYLE REQUIREMENTS:
         1. NO emojis
         2. NO hashtags
         3. Professional tone
         4. Conversational style
         5. Clear and concise writing
    
    ## OUTPUT INSTRUCTIONS
    IF the post fails ANY of the checks above:
      - Return concise, specific feedback on what to improve
      
    ELSE IF the post meets ALL requirements:
      - Call the exit_loop function
      - Return "Post meets all requirements. Exiting the refinement loop."
      
    Do not embellish your response. Either provide feedback on what to improve OR call exit_loop and return the completion message.
    
    ## POST TO REVIEW
    {current_post}
    """,
    description="Reviews post quality and provides feedback on what to improve or exits the loop if requirements are met",
    tools=[count_characters, exit_loop],
    output_key="review_feedback",
)
